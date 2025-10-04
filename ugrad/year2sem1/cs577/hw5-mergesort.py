def mergeSortInversions(arr):
    if len(arr) == 1: # base case
        return (arr, 0)

    a = arr[:len(arr)//2] # front half of arr
    b = arr[len(arr)//2:] # back half of arr
    (a, a_inv) = mergeSortInversions(a) 
    (b, b_inv) = mergeSortInversions(b)
    list_sorted = [] 
    inversions = a_inv + b_inv

    i = 0
    j = 0
    while i < len(a) and j < len(b):
        if a[i] <= b[j]:
            list_sorted.append(a[i])
            i += 1
        else:
            list_sorted.append(b[j])
            inversions += (len(a)-i)
            j += 1
    list_sorted += a[i:]
    list_sorted += b[j:]
    return list_sorted, inversions

numInst = int(input())
for i in range(numInst):
    numElements = int(input())
    elements = list(map(int, input().split()))

    print(mergeSortInversions(elements)[1])  