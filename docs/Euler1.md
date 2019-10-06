# Equazioni di eulero

[TOC]

## Abstract

Prendiamo in considerazione un modello approssimato costituito da un reticolo bidimensionale di celle quadrate di lato $ \Delta s$.

```text
+----------+----------+----------+
|          |          |          |
| i-1, j-1 | i-1, j   | i-1, j+1 |
|          |          |          |
+----------+----------+----------+
|          |          |          |
|   i, j-1 |   i, j   |   i, j+1 |
|          |          |          |
+----------+----------+----------+
|          |          |          |
| i+1, j-1 | i+1, j   | i+1, j+1 |
|          |          |          |
+----------+----------+----------+
```

Il fluido contenuta nella cella $(i, j)$ è definito da tre proprietà:

- $m_{ij} = V \rho_{ij}$ la massa,
- $\vec q_{ij} = V \rho_{ij} \vec u_{ij} $ la quantità di moto ($ \vec u_{ij} $ è la velocità del fluido)
- $ E_{ij} $ l'energia totale del fluido

la cella confina con $n$ celle adiacenti attraverso una superficie $S_k$, ogni cella adiacente è rappresentata da $(i_k, j_k)$.
Definiamo $\vec n_k $il versore uscente dalla cella $
(i,j)$ verso cella $(i_k. j_k)$.

Le equazioni di eulero non tengono conto della viscosità e della conducibilità termica del fluido.

Per il reticolo in questine abbiamo che $k = (a,b), \; a,b = 1 \dots 3 $
quindi

```math
m_{ijab} = 
\left|
    \begin{array}{rrr}
        m_{i-1,j-1} & m_{i-1,j} & m_{i-1,j+1} \\
        m_{i,j-1} & m_{i j} & m_{i,j+1} \\
        m_{i+1,j-1} & m_{i+1,j} & m_{i+1,j+1}
    \end{array}
\right| \\
q^x_{ijab} = 
\left|
    \begin{array}{rrr}
        q^x_{i-1,j-1} & q^x{i-1,j} & qu^x_{i-1,j+1} \\
        q^x_{i,j-1} & q^x_{i j} & q^x_{i,j+1} \\
        q^x_{i+1,j-1} & q^x_{i+1,j} & q^x_{i+1,j+1}
    \end{array}
\right| \\
q^y_{ijab} = 
\left|
    \begin{array}{rrr}
        q^y_{i-1,j-1} & q^y{i-1,j} & q^y_{i-1,j+1} \\
        q^y_{i,j-1} & q^y_{i j} & q^y_{i,j+1} \\
        q^y_{i+1,j-1} & q^y_{i+1,j} & q^y_{i+1,j+1}
    \end{array}
\right| \\
E_{ijab} = 
\left|
    \begin{array}{rrr}
        E_{i-1,j-1} & E_{i-1,j} & E_{i-1,j+1} \\
        E_{i,j-1} & E_{i j} & E_{i,j+1} \\
        E_{i+1,j-1} & E_{i+1,j} & E_{i+1,j+1}
    \end{array}
\right| \\
```

```math
S_{ab} =
\left|
    \begin{array}{rrr}
        0 & \Delta s & 0 \\
        \Delta s & 0 & \Delta s \\
        0 & \Delta s & 0
    \end{array}
\right| \\
n^x_{ab} =
\left|
    \begin{array}{rrr}
        -\frac{\sqrt 2}{2} & 0 & \frac{\sqrt 2}{2} \\
        -1 & 0 & 1 \\
        -\frac{\sqrt 2}{2} & 0 & \frac{\sqrt 2}{2}
    \end{array}
\right| \\
n^y_{ab} = \left|
    \begin{array}{rrr}
        -\frac{\sqrt 2}{2} & -1 & -\frac{\sqrt 2}{2} \\
        0 & 0 & 0 \\
        \frac{\sqrt 2}{2} & 1 & \frac{\sqrt 2}{2}
    \end{array}
\right|
```

## Principio di conservazione della massa

Il principio di conservazione della massa dice che

> *la variazione di massa contenuta in un volume fisso eguaglia la differenza tra i flussi di massa entranti ed i flussi di massa uscenti*

Per ogni cella adiacente a $(i,j)$ abbiamo che il flusso netto di massa entrante è dato da

```math
    \Phi_{ijk} = - (m_{i_k j_k} \vec u_{i_k j_k} + m_{ij} \vec u_{ij})\cdot \vec n_k S_k \\
    \Phi_{ij} = -\sum _k (\vec q_{i_k j_k} + \vec q_{ij}) \cdot \vec n_k S_k
```

Quindi per la cella $(j,k)$ abbiamo

```math
\Delta m_{ij} = -\sum_k (\vec q_{i_k j_k} + \vec q_{ij}) \cdot \vec n_k S_k \Delta t
```

## Principio di conservazione della quantità di moto

Il principio di conservazione della quantità di moto dice

> *la variazione nel tempo della quantità di moto del fluido contenuto nel volume di controllo, sommato al flusso netto di quantità di moto attraversante la superfice di confine, uguaglia la forza risultante delle forze esterne agenti sul fluido contenuto nel volume stesso.*

Per ogni cella adiacente a $(i,j)$ abbiamo che il flusso netto di quantità di moto entrante è

```math
\vec \Phi_{ijk} = - \left[ \vec q_{i_k j_k} (\vec u_{i_k j_k} \cdot \vec n_k) + \vec q_{ij} (\vec u_{ij} \cdot \vec n_k)\right] S_k
```

Sulla superfice $k$ agiscono le forze dovute alla pressione del fluido

```math
\vec P_{ijk} = (p_{i_k j_k} - p_{ij}) \cdot \vec n_k S_k
```

dove $p_{ij}$ è la pressione del fluido nella cella $(i,j)$.

Le forze gravitazionali

```math
\vec G_{ij} = -m_{ij} \vec g_{ij}
```

e le eventuali forze di reazione dei vincoli

```math
\vec R_{ijk}
```

Escludendo le forze di reazione abbiamo

```math
\frac{\Delta \vec {q'_{ij}}}{\Delta t} = \vec G_{ij} + \sum_k  (\vec \Phi_{ijk} + \vec P_{ijk}) \\
```

Per gli scopi di simulazione su piccola scala si ipotizzano trascurabili le forze gravitazionali.

quindi

```math
\frac{\Delta \vec {q'_{ij}}}{\Delta t} = \sum_k  (\vec \Phi_{ijk} + \vec P_{ijk}) \\
```

finalmente

```math
\Delta \vec q_{ij} =
    \Delta \vec {q'_{ij}} + \sum_k \vec R_{ijk} \Delta t
```

## Principio di conservazione dell'energia

Il principio di conservazione dell'energia dice

> *la variazione di energia totale nell'unità di tempo del fluido contenuto nel volume di controllo  sommata al flusso netto di energia totale attraverso le facce del volume di controllo uguaglia la somma della potenza delle forze agenti sull'elemento di fluido.*

Il flusso netto di energia entrante dalla superfice $k$ è dato da

```math
\Phi_{ijk} = -(E_{i_k j_k}  \vec u_{i_k j_k} + E_{ij} \vec u_{ij}) \cdot \vec n_k S_k
```

le potenze delle forze di pressione sono

```math
\Pi_{ijk} = (p_{i_k j_k} \vec u_{i_k j_k} - p_{ij} \vec u_{ij}) \cdot \vec n_k S_k
```

la potenza delle forze di gravità

```math
\Gamma_{ij} = m_{ij} \vec g_{ij} \cdot \vec u_{ij} = \vec g_{ij} \cdot \vec q_{ij}
```

le potenze delle forze di reazione sono

```math
\Rho_{ijk} = \vec R_{ijk} \cdot \vec u_{i_k j_k}
```

Quindi

```math
\Delta E_{ij} =
\left[
    \Gamma_{ij} + \sum_k (\Phi_{ijk} + \Pi_{ijk} + \Rho_{ijk})
\right] \Delta t \\
\Delta E_{ij} =
\left\{
    \vec g_{ij} \cdot \vec q_{ij}+ \sum_k
    \vec R_{ijk} \cdot u_{i_k j_k}
     - \left[
        (E_{i_k j_k} -p_{i_k j_k}) \vec u_{i_k j_k}
        + (E_{ij} -p_{ij}) \vec u_{ij}
    \right]
    \cdot \vec n_k S_k
\right\} \Delta t =

```

## Equazioni di stato

Aggiungiamo l'equazioni di stato del fluido ideale

```math
p_{ij} = \frac{m_{ij}}{V M_{mol}} T_{ij} R
```

dove $M_{mol}$ è la massa molecolare del fluido

e l'equazione di dipendenza energia e temperatura

```math
E_{ij} = \varsigma T_{ij} m_{ij}
```

Prendiamo un $ m^3 $ di l'aria formata da 79% azoto $N_2$ e 21% di ossigeno $O_2$ a 25 gradi (298K) a un'atmosfera di pressione (101 325 Pa).
Tale volume contiene

```math
n = \frac{pV}{RT} = \frac{101 325 \cdot 1}{8.31446 \cdot 298} = 40,89 mol
```

la massa molecare dell'aria è di $28.96 \frac{g}{mol}$ quindi il peso dell'aria è di $ 40.89 \cdot 28.96 = 1184 g $
La densità dell'aria è di $ 1.184 \frac{Kg}{m^2} $

## Vincolo solido

Vediamo ora l'effetto di una cella solida, ovvero una cella immobile, con densità costante ed capacità terminca nulla (non scambia calore con l'esterno).

```math
\frac{dm_{ij}}{dt} = 0 \\ 
\vec q_{ij} = \vec 0 \\
\frac{d E_{ij}}{dt} = 0
```

Prendiamo ora come riferimento una cella adiacente $(i,j)$ tale che|$(i_k, j_k)$ è una cella vincolata.

Il vincolo impone che il flusso di massa uscente dalla cella $(i,j)$ attraverso la superfice di contatto $k$ sia nullo. quindi deve essere sempre

```math
    \vec q_{ij} \cdot \vec n_k \le 0
```

per cui

```math
(\vec q_{ij} + \Delta \vec q_{ij}) \cdot \vec n_k \le 0 \\
```

Sia ora

```math
\Delta \vec {q'_{ij}} =
\left[
\vec G_{ij} + \sum_k  (\vec \Phi_{ijk} + \vec P_{ijk})
\right] \Delta t
```

la variazione di quantità di moto senza le forze reattive, se 

```math
(\vec q_{ij} + \Delta \vec {q'_{ij}}) \cdot \vec n_k > 0
```

allora la superfice vincolata reagirà con un forza tale da contrastare la variazione di quantità di moto, quindi

```math
(\vec q_{ij} + \Delta \vec q_{ij}) \cdot \vec n_k = 0 \\

\Delta \vec q_{ij} \cdot \vec n_k = - \vec q_{ij} \cdot \vec n_k \\

(\Delta \vec {q'_{ij}} + \vec R_{ijk} \Delta t) \cdot \vec n_k = - \vec q_{ij} \cdot \vec n_k \\

\vec R_{ijk} \Delta t\cdot \vec n_k = - (\vec q_{ij} + \Delta \vec {q'_{ij}}) \cdot \vec n_k \\

\vec R_{ijk} \Delta t = - [(\vec q_{ij} + \Delta \vec {q'_{ij}}) \cdot \vec n_k] \vec n_k \\

\vec R_{ijk} \Delta t = -(\vec q_{ij} \cdot \vec n_k) \vec n_k
- (\vec \Phi_{ij} \cdot \vec n_k) \vec n_k \Delta t 
- (\vec P_{ij} \cdot \vec n_k) \vec n_k \Delta t 

```

Per quanto concerne il principio di conservazione dell'energia non essendoci scambio di calore
tra le celle vincolate e le celle adiacenti rimane valida l'equazione principale.

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
