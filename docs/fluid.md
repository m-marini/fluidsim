# Fluidi

[TOC]

## Update relation

Equazione di relazione $i$ rispetto le cella adiacenti $j$:

```math
R_i(t+dt) = A_{ij} \cdot  V_j(t) + B_{ij} \cdot R_i(t) + C_i
```

Equazione di cella $i$ rispetto le relazioni adiacenti $j$

```math
V_i(t+dt) = A_{ij} \cdot R_j(t) + B_{ij} \cdot V_i(t) + C_i
```

## Equazioni di Eulero dei fluidi non viscosi

La simulazione avviene in una suerfice bidimensionale (sezione).

Prendiamo una cella elementare di forma qualsiasi.
Nel caso esemplificativo un quadrato, ma nella simulazione sarà una cella esagonale.

```text
    +-------+
    |       |
    |  Ci   |
    |       |
    +-------+
```

### Equazione di continuità o bilancio di massa

```math
dm_{ij} = k_{ij} \cdot m_i \cdot dt \\
dm_{ji} = k_{ij} \cdot m_j \cdot dt \\
k_{ij} = k_{ji} = - \left( \frac{Q_j}{m_j} - \frac{Q_i}{m_i} \right) N_{ij} \frac{S_{ij}}{v}
```

### Equazione di bilancio di quantità di moto

```math
dQ_{ij} = Q_i k_{ij} dt^2 + F_{ij} dt \\
dQ_{ji} = Q_j k_{ij} dt^2 + F_{ij} dt \\
F_{ij} = F_{ji} = ( m_j - m_i ) C S_{ij} N_{ij} \\
dQ_i = g_i m_i dt \\
C = \frac{R T}{p m v}
```
