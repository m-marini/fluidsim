Fluidi
======


Update relation
---------------

Equazione di relazione `i` rispetto le cella adiacenti`j`:

    R(i,t+dt) = A(i,j) * V(j,t) + B(i,j) * R(i,t) + C(i)

Equazione di cella `i` rispetto le relazioni adiacenti `j`

    V(i,t+dt) = A(i,j) * R(j,t) + B(i,j) * V(i,t) + C(i)


Equazioni di Eulero dei fluidi non viscosi
------------------------------------------

La simulazione avviene in una suerfice bidimensionale (sezione).

Prendiamo una cella elementare di forma qualsiasi.
Nel caso esemplificativo un quadrato, ma nella simulazione sarà una cella esagonale.

    +-------+
    |       |
    | C(i)  |
    |       |
    +-------+

### Equazione di continuità o bilancio di massa ###

    dm(i,j) = k(i,j) m(i) dt
    dm(j,i) = k(i,j) m(j) dt
    k(i,j) = k(j,i) = - ( Q(j) / m(j) - Q(i) / m(i) ) N(i,j) S(i,j) / v


### Equazione di bilancio di quantità di moto ###

    dQ(i,j) = Q(i) k(i,j) dt^2 + F(i,j) dt
    dQ(j,i) = Q(j) k(i,j) dt^2 + F(i,j) dt
    F(i,j) = F(j,i) = ( m(j) - m(i) ) C S(i,j) N(i,j)
    dQ(i) = g(i) m(i) dt
    C = R T / (pm v)