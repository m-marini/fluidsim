# Convoluzione

[TOC]

## Funzione convolutiva

In generale definiamo

```math
    Y_{ijk} = \sum_{a,b,c} F_{abc}(i,j,k) P_{abc}(i,j) \\
    a,b = 1 \dots 3, c = 1 \dots h
```

Quindi

```math
    Y_{ijk} = F_{111}(i,j,k) P_{111}(i,j) + ... + F_{33h}(i,j,k) P_{33h}(i,j)
```

## Topologia

Poniamo che $ X(i,j), Y(i,j) $ siano le coordinate nel punto $(i,j)$.

Poniamo che

```math
P^X_{ab}(i,j) = X(i + a - 2, j + b -2)\\
P^Y_{ab}(i,j) = Y(i + a - 2, j + b -2)
```

Definiamo

```math
dX_{ab}(i,j) = X(i + a - 2, j + b -2) - X(i, j)\\
 \\
dY_{ab}(i,j) = Y(i + a - 2, j + b -2) - Y(i, j)\\
```

poi calcoliamo

```math
\Delta S_{ab}(i,j) = \sqrt{dX^2_{ab}(i,j) + dY^2_{ab}(i, j)}
```

### Topologia reticolo quadrato

Il reticolo quadrato è definito dalle equzione delle coordinate come

```math
X(i,j) = X_0 + (j - 1) \Delta s \\
Y(i,j) = Y_0 + (i - 1) \Delta s
```

Da cui deriva

```math
dX_{ab}(i,j) = X_1 + (j + b - 3) \Delta s - X_1 - (j - 1) \Delta s = (b - 2) \Delta s \\
\,\\
dX_{ab}(i,j) = \left| \begin{array}{rrr}
    -1 & 0 & 1  \\
    -1 & 0 & 1 \\
    -1 & 0 & 1 \\
    \end{array}
    \right| \Delta s \\
\,\\
dY_{ab}(i,j) = Y_1 + (i + a - 2) \Delta s - Y_1 - i \alpha = (a - 2) \Delta s \\
\,\\
dY_{ab}(i,j) = \left| \begin{array}{rrr}
    -1 & -1 & -1  \\
    0 & 0 & 0 \\
    1 & 1 & 1 \\
    \end{array}
    \right| \Delta s
\,\\
dS_{ab}(i,j) = \left| \begin{array}{rrr}
    \sqrt{2} & 1 & \sqrt{2}  \\
    1 & 0 & 1 \\
    \sqrt{2} & 1 & \sqrt{2} \\
    \end{array}
    \right| \Delta s
```

### Topologia reticolo isometrico

```text
   +---+---+---+
  / \ / \ / \ /
 +---+---+---+
  \ / \ / \ / \
   +---+---+---+
  / \ / \ / \ /
 +---+---+---+
```

Nel reticolo isometrico a nido d'ape si ha

```math
    Y(i,j) =  Y_0 + (i - 1) \sin \left( \frac{\pi}{3} \right) \Delta s = Y_0 + (i - 1) \frac{\sqrt 3}{2} \Delta s 
```

da cui

```math
dY_{ab}(i,j) = Y_0 + (i + a - 3) \cos \left( \frac{\pi}{3} \right) \Delta s - Y_0 - (i - 1) \cos \left( \frac{\pi}{3} \right) \Delta s = \\
 = (a - 2) \frac{\sqrt3}{2} \Delta s \\
\;\\
dY_{ab}(i,j) =
    = \left| \begin{array}{rrr}
    -1 & -1 & -1  \\
    0 & 0 & 0 \\
    1 & 1 & 1 \\
    \end{array}
    \right| \frac{\sqrt3}{2} \Delta s
```

La funzione $ X $ cambia

#### $ X(i,j) $ per $ i $ dispari

```math
 X(i, j) = X_0 + (j - 1) \Delta s \\
```

#### $ X(i,j) $ per $ i $ pari

```math
 X(i, j) = X_0 + \left( j - 1 + \frac{1}{2} \right)\Delta s = 
 X_0 + \left( j - \frac{1}{2} \right) \Delta s \\
```

#### $ dX_{ab}(i,j) $ per $ i $ dispari

Quando $ a = 2 $ abbiamo che $ i + a - 2 = i $ è dispari quindi

```math
dX_{ab}(i,j) = X_0 + (j + b -3) \Delta s - X_0 - (j - 1) \Delta s = (b - 2)\Delta s
```

mentre per $ a \in (1,3) $ si ha che $ i + a - 2 $ è pari quindi

```math
dX_{ab}(i,j) =
 X_0 + \left( j + b - 2 - \frac{1}{2} \right) \Delta s - X_0 - (j - 1) \Delta s = \left( b - \frac{3}{2} \right) \Delta s
```

la matrice risultate è

```math
dX_{ab}(i,j)  = \left| \begin{array}{rrr}
    -1 & 1 & 3  \\
    -2 & 0 & 2 \\
    -1 & 1 & 3  \\
    \end{array}
    \right| \frac{\Delta s}{2}
```

```math
dS_{ab}(i,j)  = \left| \begin{array}{rrr}
    1 & 1 & \frac{\sqrt 6}{2}  \\
    1 & 0 & 1 \\
    1 & 1 & \frac{\sqrt 6}{2}  \\
    \end{array}
    \right| \Delta s
```

#### $ dX_{ab}(i,j) $ per $ i $ pari

Quando $ a = 2 $ abbiamo che $ i + a - 2 = i $ è pari quindi

```math
dX_{ab}(i,j) =
 X_0 + \left( j + b - 2 - \frac{1}{2} \right) \Delta s -
 X_0 - \left( j - \frac{1}{2} \right) \Delta s = (b - 2) \Delta s
```

mentre per $ a \in (1,3) $ si ha che $ i + a - 2 $ è dispari quindi

```math
dX_{ab}(i,j) =
 X_0 + (j + b - 2 - 1) \alpha - X_0 - \left( j - \frac{1}{2} \right) \Delta s = \\
 = \left( b - \frac{5}{2} \right) \Delta s
```

la matrice risultate è

```math
dX_{ab}(i,j)  = \left| \begin{array}{rrr}
    -3 & -1 & 1  \\
    -2 & 0 & 2 \\
    -3 & -1 & 1  \\
    \end{array}
    \right| \frac{\Delta s}{2}
```

```math
dS_{ab}(i,j)  = \left| \begin{array}{rrr}
    \frac{\sqrt 6}{2} & 1 & 1 \\
    1 & 0 & 1 \\
    \frac{\sqrt 6}{2} & 1 & 1 \\
    \end{array}
    \right| \Delta s
```

## Gradiente

Sia ora $ F(x, y) $ una funzione scalare di $x, y$.

Il gradiente di $ F $ è definito come

```math
G_x = \lim_{dx \rightarrow 0} \frac {F(x + dx, y) - F(x, y)}{dx} \\
G_y = \lim_{dy \rightarrow 0} \frac {F(x, y + dy) - F(x, y)}{dy} \\
```

In pratica sia $ F_{ij} $ la matrice di valori della funzione F nel reticolo.

Per calcolare il gradiente calcoliamo la media dei gradienti nelle direzioni adiacenti il punto $(i,j)$.

Definiamo $F'_{ab}(i,j) $ la matrice di adiacenza rispetto il punto $(i,j)$ e calcoliamo.

```math
    \Delta F_{ab}(i,j) = F'_{ab}(i,j) - F'_{22}(i,j)
```

```math
G^x_{ab}(i,j) = \frac{\Delta F_{ab}}{\Delta S_{ab}} \frac{dX_{ab}}{\Delta S_{ab}} \\
G^y_{ab}(i,j) = \frac{\Delta F_{ab}}{\Delta S_{ab}} \frac{dY_{ab}}{\Delta S_{ab}}
```

Le precedente possiamo riscriverla con

```math
G^x_{ab}(i,j) = \Delta F_{ab} J^x_{ab} \\
G^y_{ab}(i,j) = \Delta F_{ab} J^y_{ab}
```

dove

```math
J^x_{ab} = \frac{dX_{ab}}{\Delta S^2_{ab}}\\
J^y_{ab} = \frac{dY_{ab}}{\Delta S^2_{ab}}
```

Quindi calcoliamo la media sulle celle adiacenti.

```math
G^x_{ab}(i,j) = \frac{1}{n} \sum_{(a,b)} \frac{\Delta F_{ab}}{\Delta S_{ab}} \frac{dX_{ab}}{\Delta S_{ab}} \\
G^y_{ab}(i,j) = \frac{1}{n} \sum_{(a,b)} \frac{\Delta F_{ab}}{\Delta S_{ab}} \frac{dY_{ab}}{\Delta S_{ab}}
```

Calcoliamo ora $J^x$ e $J^y$ per i vari reticoli

### J per il reticolo quadrato

Consideriamo adiacenti solo le celle laterali e non diagonali avremo quindi $ n = 4 $ e 

```math
J^x_{ab}(i,j) = \left| \begin{array}{rrr}
    0 & 0 & 0  \\
    -1 & 0 & 1 \\
    0 & 0 & 0  \\
    \end{array}
    \right| \frac{1}{\Delta s}\\
J^y_{ab}(i,j) = \left| \begin{array}{rrr}
    0 & -1 & 0  \\
    0 & 0 & 0 \\
    0 & 1 & 0  \\
    \end{array}
    \right| \frac{1}{\Delta s}

```

### J per il reticolo isometico con $ i $ dispari

Le celle adiacenti sono 6 e le matrici risultatni sono

```math
J^x_{ab}(i,j) = \left| \begin{array}{rrr}
    -1 & 1 & 0  \\
    -2 & 0 & 2 \\
    -1 & 1 & 0  \\
    \end{array}
    \right| \frac{1}{2 \Delta s}\\
J^y_{ab}(i,j) = \left| \begin{array}{rrr}
    -1 & -1 & 0  \\
    0 & 0 & 0 \\
    1 & 1 & 0  \\
    \end{array}
    \right| \frac{\sqrt 3}{2 \Delta s}

```

### J per il reticolo isometico con $ i $ pari

Le celle adiacenti sono 6 e le matrici risultatni sono

```math
J^x_{ab}(i,j) = \left| \begin{array}{rrr}
    0 & -1 & 1  \\
    -2 & 0 & 2 \\
    0 & -1 & 1  \\
    \end{array}
    \right| \frac{1}{2 \Delta s}\\
J^y_{ab}(i,j) = \left| \begin{array}{rrr}
    0 & -1 & -1  \\
    0 & 0 & 0 \\
    0 & 1 & 1  \\
    \end{array}
    \right| \frac{\sqrt 3}{2 \Delta s}
```
