from datetime import datetime
import time
from random import randint

for i in range(3):
  for x in range(9999999):
    f1 = open("data.log", "a")
    now = datetime.now()
    c1 = now.strftime('%Y-%m-%d %H:%M:%S')
    a = randint(50, 100000)
    c2 = a * 100 
    c3 = randint(1, 100000)
    c4 = randint(1,100)
    f1.write(c1+","+str(c2)+","+str(c3)+","+str(c4)+"\n")

    time.sleep(randint(10, 25)/10)
    f1.close()
