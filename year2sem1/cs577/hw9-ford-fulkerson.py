from collections import deque

class Graph:
 
    def __init__(self, graph):
        self.graph = graph
        self.row = len(graph)
 
    def BFS(self, s, t, parent):
        visited = [False]*(self.row)
        queue = deque()
        queue.append(s)
        visited[s] = True
        
        while queue:
            u = queue.popleft()

            for ind, val in enumerate(self.graph[u]):
                if visited[ind] == False and val > 0:
                    queue.append(ind)
                    visited[ind] = True
                    parent[ind] = u
                    if ind == t:
                        return True
        return False
             
    def FordFulkerson(self, source, sink):
        parent = [-1]*(self.row)
        max_flow = 0

        while self.BFS(source, sink, parent):
            path_flow = float("Inf")
            s = sink
            while(s !=  source):
                path_flow = min (path_flow, self.graph[parent[s]][s])
                s = parent[s]

            max_flow +=  path_flow

            v = sink
            while(v !=  source):
                u = parent[v]
                self.graph[u][v] -= path_flow
                self.graph[v][u] += path_flow
                v = parent[v]
 
        return max_flow

numInst = int(input())
for i in range(numInst):
    numNodes, numEdges = list(map(int, input().split()))
    g = [[0]*numNodes for i in range(numNodes)]
    for j in range(numEdges):
        s, t, c = list(map(int, input().split()))
        g[s-1][t-1] = c
    g = Graph(g)
    source = 1
    sink = numNodes
    print(g.FordFulkerson(source, sink))
