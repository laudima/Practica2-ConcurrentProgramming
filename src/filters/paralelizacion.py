

# n= numero de procesadores, p= fraccion de codigo paralelizable
def amdhal_law(n, p):
    return 1 / ((1 - p) + p/n)

def __main__():
    n = input("Introduce el numero de procesadores: ")
    p = input("Introduce la fraccion de codigo paralelizable: ")

    print(1(n, p))