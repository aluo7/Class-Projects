# numInst = int(input())
# for i in range(numInst):
#     max = int(input())  # max number of pages in cache
#     num_req = int(input())  # number of requests to be made
#     reqs = list(map(int, input().split()))  # list of all requests

#     page_faults = 0
#     page = []
#     for i in range(max):
#         page.append(-1)

#     for i in range(num_req):
#         flag = 0
#         for j in range(max):
#             if(page[j] == reqs[i]):
#                 flag = 1
#                 break
            
#         if flag == 0:
#             # look for an empty one
#             faulted = False
#             new_slot = -1
#             for q in range(max):
#                 if page[q] == -1:
#                     faulted = True
#                     new_slot = q
            
#             if not faulted:
#                 # find next use farthest in future
#                 max_future = 0
#                 max_future_q = -1
#                 for q in range(max):
#                     if page[q] != -1:
#                         found = False
#                         for ii in range(i, num_req):
#                             if reqs[ii] == page[q]:
#                                 found = True
#                                 if ii > max_future:
#                                     max_future = ii
#                                     max_future_q = q

#                                 break
                        
#                         if not found:
#                             max_future_q = q
#                             break

#                 faulted = True
#                 new_slot = max_future_q
            
#             page_faults += 1
#             page[new_slot] = reqs[i]
#     print(page_faults)
