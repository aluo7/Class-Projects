def sort_key(job):
    return job[1]

numInst = int(input())
for x in range(numInst):
    numJobs = int(input())
    jobs = []
    for y in range(numJobs):
        start, end = input().split()
        job = (int(start), int(end))
        jobs.append(job)
    jobs.sort(key = sort_key)

    S = []
    prevEnd = 0
    for job in jobs:
        if prevEnd <= job[0]:
            S.append(job)
            prevEnd = job[1]
    print(len(S)) 