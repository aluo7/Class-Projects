# project: p3
# submitter: aluo7
# partner: none
# hours: 5

from collections import deque
import pandas as pd
import time
import requests

class GraphSearcher:
    def __init__(self):
        self.visited = set()
        self.order = []

    def visit_and_get_children(self, node):
        """ Record the node value in self.order, and return its children
        param: node
        return: children of the given node
        """
        raise Exception("must be overridden in sub classes -- don't change me here!")

    def dfs_search(self, node):
        self.visited.clear()
        self.order.clear()
        
        self.dfs_visit(node)

    def dfs_visit(self, node):
        if node in self.visited:
            return
        
        self.visited.add(node)
        children = self.visit_and_get_children(node)
        
        for child in children:
            self.dfs_visit(child)
    
    def bfs_search(self, node):
        self.visited.clear()
        self.order.clear()
        
        self.bfs_visit(node)
        
    def bfs_visit(self, node):
        queue = deque([node])
        
        while len(queue) > 0:
            curr_node = queue.popleft()
            
            self.visited.add(curr_node)
            children = self.visit_and_get_children(curr_node)
            
            for child in children:
                if child not in self.visited and child not in queue:
                    queue.append(child)
                    
            
class MatrixSearcher(GraphSearcher):
    def __init__(self, df):
        super().__init__() # call constructor method of parent class
        self.df = df

    def visit_and_get_children(self, node):
        # TODO: Record the node value in self.order
        self.order.append(node)
        
        children = []
        # TODO: use `self.df` to determine what children the node has and append them
        for node, has_edge in self.df.loc[node].items():
            if has_edge == 1:
                children.append(node)
        return children
    
    
class FileSearcher(GraphSearcher):
    def __init__(self):
        super().__init__()
        
    def visit_and_get_children(self, file):
        with open("file_nodes/"+file) as f:
            contents = f.readlines()
            
        final = []
        for x in contents:
            final.append(x.strip("\n"))
            
        self.order.append(contents[0].strip("\n"))
        return final[1].split(",")
        
    def concat_order(self):
        return ''.join([str(n) for n in self.order])
    

class WebSearcher(GraphSearcher):
    def __init__(self, driver, url = None):
        super().__init__()
        self.driver = driver
        self.frag = None
        
        if url != None:
            self.frag = pd.read_html(url)[0]
        
    def visit_and_get_children(self, url):
        children = []
        self.driver.get(url)
        self.order.append(url)
        
        a_source = self.driver.find_elements(by = "tag name", value = "a")
        for a in a_source:
            child_url = a.get_attribute("href")
            children.append(child_url)
            
        self.frag = pd.read_html(url)[0]
        
        return children
    
    def table(self):
        df_list = []
        for child_url in self.order:
            fragment = WebSearcher(self.driver, child_url).frag
            df_list.append(fragment)
        new_df = pd.concat(df_list, ignore_index = True)
        return new_df
    
def reveal_secrets(driver, url, travellog):
    clues = []
    password = [clues.append(x) for x in travellog["clue"]]
    password = ''.join([str(n) for n in clues])
    
    driver.get(url)
    
    text = driver.find_element("id", "password")
    pw_btn = driver.find_element("id", "attempt-button")
    text.send_keys(password)
    pw_btn.click()
    
    time.sleep(1)
    
    loc_btn = driver.find_element("id", "securityBtn")
    loc_btn.click()
    
    time.sleep(1)
    
    img_url = driver.find_element("id", "image").get_attribute("src")
    r = requests.get(img_url)
    
    if r.status_code == 200:
        with open("Current_Location.jpg", "wb") as f:
            f.write(r.content)
            
    loc = driver.find_element("id", "location").text
    return loc