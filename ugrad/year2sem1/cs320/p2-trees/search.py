class Node():
    def __init__(self, key):
        self.key = key
        self.values = []
        self.left = None
        self.right = None
        
    def __len__(self):
        size = len(self.values)
        if self.left != None:
            size += len(self.left.values)
        if self.right != None:
            size += len(self.right.values)
        return size
    
    def lookup(self, key):
        if self.key == key:
            return self.values
        if self.key > key and self.left != None:
            return self.left.lookup(key)
        if self.key < key and self.right != None:
            return self.right.lookup(key)
        return []
    
    def height(self):
        if self.left == None:
            x = 0
        else:
            x = self.left.height()

        if self.right == None:
            y = 0
        else:
            y = self.right.height()

        return max(x, y) + 1
    
    def leaf(self):
        if self.left == None and self.right == None:
            return 1
        
        if self.left == None and self.right != None:
            x = 0
            y = self.right.leaf()
        
        if self.right == None and self.left != None:
            x = self.left.leaf()
            y = 0
            
        if self.left != None and self.right != None:
            x = self.left.leaf()
            y = self.right.leaf()
            
        return x+y
    
    def second_highest(self):
        curr = self
        traversed = [curr]
        while curr.right != None:
            curr = curr.right
            traversed.append(curr)
            
        if curr.left != None:
            return curr.left.key
            
        return traversed[-2].key
        

class BST():
    def __init__(self):
        self.root = None

    def add(self, key, val):
        if self.root == None:
            self.root = Node(key)

        curr = self.root
        while True:
            if key < curr.key:
                # go left
                if curr.left == None:
                    curr.left = Node(key)
                curr = curr.left
            elif key > curr.key:
                # go right
                if curr.right == None:
                    curr.right = Node(key)
                curr = curr.right
            else:
                # found it!
                assert curr.key == key
                break

        curr.values.append(val)
        
    def __dump(self, node):
        if node == None:
            return
        self.__dump(node.right)            # 1
        print(node.key, ":", node.values)  # 2
        self.__dump(node.left)             # 3

    def dump(self):
        self.__dump(self.root)
        
    def __getitem__(self, index):
        return self.root.lookup(index)