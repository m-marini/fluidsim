# Equazioni di eulero

[TOC]


## Ottimizzazione numerica

### Proiezione lungo la direzione $ \vec n$

```math
\vec b = (\vec a \cdot \vec n) \vec n
\\
b_i = (\sum_j a_j n_j) n_i
= \sum_j a_j (n_j n_i)
\\
\vec b = (\vec n \otimes \vec n) \cdot \vec a
```

### Flusso netto

Posto

```math
F(\vec A,i,j) = f_{ab}(\vec A,i,j) =
\left|
\begin{array}{lll}
  \vec a_{i-1,j-1} & \vec a_{i-1,j} & \vec a_{i-1,j+1} \\
  \vec a_{i,j-1}   & \vec a_{i,j}   & \vec a_{i,j+1} \\
  \vec a_{i+1,j-1} & \vec a_{i+1,j} & \vec a_{i+1,j+1}
\end{array}
\right|\\
```

abbiamo

```math
\vec A' = \vec F(\vec A,i,j) \\

\vec B = \vec b_k = \vec b_{ab} = \vec a_{i_k j_k} + \vec a_{ij}\\

\vec B = \left|
\begin{array}{lll}
  \vec a'_{11} + a'_{22} & \vec a'_{12} + a'_{22} & \vec a'_{13} + a'_{22} \\
  \vec a'_{21} + a'_{22} & \vec 0                 &\vec a'_{23} + a'_{22} \\
 \vec  a'_{31} + a'_{22} & \vec a'_{32} + a'_{22} & \vec a'_{33} + a'_{22}
\end{array}
\right| = \\

\vec B = \Phi_{3 \times 3 \times 3 \times 3} \vec A' =
\sum_{cd} \phi_{abcd} \vec a'_{cd}\\

\vec b_{ab} = \vec a'_{ab} + \vec a'_{22} \Rightarrow \phi_{abab} = \phi_{ab22} = 1 \forall (a,b) \ne (2,2) \\

b_{abc} = \sum_{d,e} \phi_{abde} c_{dec}
```

### Differenza di pressione

```math
A' = F(A,i,j) \\

B = b_k = b_{ab} = a_{i_k j_k} - a_{ij}\\

B = \left|
\begin{array}{rrr}
  a'_{11} - a'_{22} & a'_{12} - a'_{22} & a'_{13} - a'_{22} \\
  a'_{21} - a'_{22} & 0 & a'_{23} - a'_{22} \\
  a'_{31} - a'_{22} & a'_{32} - a'_{22} & a'_{33} - a'_{22}
\end{array}
\right| = \\

B = \Theta_{3 \times 3 \times 3 \times 3} A' =
\sum_{cd} \theta_{abcd} a'_{cd}\\
b_{ab} = a'_{ab} - a'_{22} \Rightarrow \phi_{abab} = -\phi_{ab22} = 1
```

```math
\Delta m_{ij} = -\sum_k (\vec q_{i_k j_k} + \vec q_{ij}) \cdot \vec n_k S_k \Delta t
```

### Quantità per cella

Estrapoliamo ora dalle equazioni le quantità condivise che possono quindi essere calcolate una volta sola.

Quantità legate alle celle

```math
    \vec u_{ij} = \frac{\vec q_{ij}}{m} \\
    \vec G_{ij} = m_{ij} \vec g_{ij} \\
    p_{ij} = \frac{R}{V M_{mol}\varsigma} E_{ij} \\
    \Gamma_{ij} = \vec g_{ij} \cdot \vec q_{ij} \\
    \vec D_{ij} = \left[
        (E_{ij} -p_{ij}) \vec u_{ij}
    \right]
```

### Quantità per superficie

Per superfice invece abbiamo

```math
    \vec S_k = \vec n_k S_k
```

### Equazioni tensoriali

Posto

```math
    \mu_{ij}
```

La matrice di vincoli sulla massa con valore 1 se la cella è vincolata.

le equazioni di variazione di massa risultano

```math
\Delta m_{ij} = -(1-\mu_{ij}) \sum_k (\vec q_{i_k j_k} + \vec q_{ij}) \cdot \vec  S_k \Delta t \\

\Delta m_(i,j) = -(1-\mu_{ij}) \sum_{abcdf} \phi_{abcd} q'_{cdf}(i,j) s_{abf} \Delta t\\
```

deriviamo le equazioni di flusso di quantità di moto

```math
\vec q_{i_k j_k} (\vec u_{i_k j_k} \cdot \vec n_k) = \sum_d q'_{abc}(i,j) u'_{abd}(i,j) n_{abd}\\

\vec q_{ij} (\vec u_{ij} \cdot \vec n_k) = \sum_d q'_{22c}(i,j) u'_{22d}(i,j) n_{abd} \\

\vec q_{i_k j_k} (\vec u_{i_k j_k} \cdot \vec n_k)
    - \vec q_{ij} (u_{ij} \cdot \vec n_k)
= \sum_d
\left(
    q'_{abc}(i,j) u'_{abd}(i,j) + q'_{22c}(i,j) u'_{22d}(i,j)
\right) n_{abd} = \\
= \sum_{dfg} \phi_{abfg} q'_{fgc}(i,j) u'_{fgd}(i,j) n_{abd} \\

\Phi^q_{abc}(i,j) = -\sum_{dfg} \phi_{abfg} q'_{fgc}(i,j) u'_{fgd}(i,j) s_{abd}
```

le equazioni delle forze di pressione agenti sull'elemento di volume sono

```math
\vec \Pi(i,j) = (p_{ij} - p_{i_k j_k}) \vec n_k S_k \\

\Pi_{abc} = -(p_{ab} - p_{22}) n_{abc} s_{ab}

```

le equazioni delle forze di reazione sono

```math
\vec R_{ijk} = - \frac{1}{\Delta t }[(\vec q_{ij} + \Delta \vec {q'_{ij}}) \cdot \vec n_k] \vec n_k \\

R_{abc}(i,j) = - \frac{1}{\Delta t}
\sum_d
\left[
    q'_d(i,j) + \Delta q'_d(i,j)
\right] n_{abd} n_{abc}\\

\Delta q'_c(i,j) = - \Delta t \sum_{ab} \Phi^q_{abc}(i,j) + \Pi_{abc}(i,j)\\

R_{abc}(i,j) = -\frac{1}{\Delta t}\sum_d
\left[
    q'_d(i,j)
\right] n_{abd} n_{abc} +
\sum_{fgd}
\left[
    \Phi^q_{fgd}(i,j) + \Pi_{fgd}(i,j)
\right] n_{abd} n_{abc} +
```

...

```math
\frac{\Delta \vec {q'_{ij}}}{\Delta t} =  m_{ij} \vec g_{ij} + \sum_k
\left[
    - \vec q_{i_k j_k}
    \left(
        \vec u_{i_k j_k} \cdot \vec n_k
    \right)
    - \vec q_{ij}
    \left(
        u_{ij} \cdot \vec n_k
    \right)
    + (p_{ij} - p_{i_k j_k}) \vec n_k
\right]
S_k \\


\Delta \vec q_{ij} =
    \Delta \vec {q'_{ij}} + \sum_k \vec R_{ijk} \Delta t \\

\Delta E_{ij} =
\left[
    \vec \Gamma_{ij} + \sum_k
    \vec R_{ijk} \cdot u_{i_k j_k}
     - \left(
        D_{i_k j_k} \vec u_{i_k j_k}
        + D_{ij} \vec u_{ij}
    \right)
    \cdot \vec S_k
\right] \Delta t
```
