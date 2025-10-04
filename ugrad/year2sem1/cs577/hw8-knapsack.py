def knapSack(cap, wts, vals):
    K = [[0 for x in range(cap + 1)] for x in range(len(vals) + 1)]
 
    for i in range(len(vals) + 1):
        for w in range(cap + 1):
            if i == 0 or w == 0:
                K[i][w] = 0
            elif wts[i-1] <= w:
                K[i][w] = max(vals[i-1]
                          + K[i-1][w-wts[i-1]], 
                              K[i-1][w])
            else:
                K[i][w] = K[i-1][w]
 
    return K[len(vals)][cap]

numInst = int(input())
for i in range(numInst):
    numItems, capacity = list(map(int, input().split()))
    weights = []
    vals = []
    for j in range(numItems):
        weight, val = list(map(int, input().split()))
        weights.append(weight)
        vals.append(val)
    print(knapSack(capacity, weights, vals))