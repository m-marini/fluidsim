# Equazioni di eulero

[TOC]

## Abstract

Prendiamo in considerazione un modello approssimato costituito da un reticolo bidimensionale di celle quadrate di lato $ \Delta s$.

```text
+---+---+---+
|   |   |   |
+---+---+---+
|   |   |   |
+---+---+---+
|   |   |   |
+---+---+---+
```

Prendiamo come riferimento dello spazio gli assi

```text
             x
   +--------->
   |
   |
   |
   |
y  V
```

Il fluido contenuta nella cella $(i, j)$ è definito da tre proprietà:

- $m_{ij} = V \rho_{ij}$ la massa,
- $\vec q_{ij} = V \rho_{ij} \vec u_{ij} $ la quantità di moto ($ \vec u_{ij} $ è la velocità del fluido)
- $ E_{ij} $ l'energia totale del fluido

la cella confina con $n$ celle adiacenti attraverso una superficie $S_k$, ogni cella adiacente è rappresentata da $(i_k, j_k)$.
Definiamo $\vec n_k $il versore uscente dalla cella $
(i,j)$ verso cella $(i_k. j_k)$.

Le equazioni di eulero non tengono conto della viscosità e della conducibilità termica del fluido.

Possiamo rappresentare le proprietà locali del fluido nel reticolo con un matrici $3 \times 3 $

```math
m'_{ab}(i,j) =
\left|
    \begin{array}{rrr}
        m_{i-1,j-1} & m_{i-1,j} & m_{i-1,j+1} \\
        m_{i,j-1} & m_{i j} & m_{i,j+1} \\
        m_{i+1,j-1} & m_{i+1,j} & m_{i+1,j+1}
    \end{array}
\right| \\
q'_{abc}(i,j) =
\left|
    \begin{array}{rrr}
        (q_{i-1,j-1,2},q_{i-1,j-1,1}) & (q_{i-1,j,2},q_{i-1,j,1}) & (q_{i-1,j+1,2},q_{i-1,j+1,1}) \\
        (q_{i,j-1,2},q_{i,j-1,1}) & (q_{i, j,2},q_{i, j,1}) & (q_{i,j+1,2},q_{i,j+1,1}) \\
        (q_{i+1,j-1,2},q_{i+1,j-1,1}) & (q_{i+1,j,2},q_{i+1,j,1}) & (q_{i+1,j+1,2},q_{i+1,j+1,1})
    \end{array}
\right| \\
E'_{ab}(i,j) =
\left|
    \begin{array}{rrr}
        E_{i-1,j-1} & E_{i-1,j} & E_{i-1,j+1} \\
        E_{i,j-1} & E_{i j} & E_{i,j+1} \\
        E_{i+1,j-1} & E_{i+1,j} & E_{i+1,j+1}
    \end{array}
\right| \\

S_{ab} =
\left|
    \begin{array}{rrr}
        0 & \Delta s & 0 \\
        \Delta s & 0 & \Delta s \\
        0 & \Delta s & 0
    \end{array}
\right| \\
n_{abc} =
\left|
    \begin{array}{rrr}
        (-\frac{\sqrt 2}{2},-\frac{\sqrt 2}{2}) & (0,-1) & (\frac{\sqrt 2}{2},-\frac{\sqrt 2}{2}) \\
        (-1,0) & (0,0) & (1,0) \\
        (-\frac{\sqrt 2}{2},\frac{\sqrt 2}{2}) & (0,1) & (\frac{\sqrt 2}{2}, \frac{\sqrt 2}{2})
    \end{array}
\right|
```

## Principio di conservazione della massa

Il principio di conservazione della massa dice che

> *la variazione di massa contenuta in un volume fisso eguaglia la differenza tra i flussi di massa entranti ed i flussi di massa uscenti*

Per ogni cella adiacente a $(i,j)$ abbiamo che il flusso netto di massa entrante è dato da

```math
    \Phi^m_{ab}(i,j) = - m'_{ab}(i,j) \vec u'_{ab}(i,j) \cdot \vec n_{ab} S_{ab} - m'_{22}(i,j) \vec u'_{22}(i,j) \cdot \vec n_{ab} S_{ab}\\
    \Phi^m_{ab}(i,j) = -(\vec q'_{ab}(i,j) + \vec q'_{22}(i,j)) \cdot \vec n_{ab} S_k
```

Quindi per la cella $(j,k)$ abbiamo

```math
\Delta m_{ij} =-\sum_{ab} (\vec q'_{ab}(i,j) + \vec q'_{22}(i,j)) \cdot \vec n_{ab} S_k \Delta t
```

## Principio di conservazione della quantità di moto

Il principio di conservazione della quantità di moto dice

> *la variazione nel tempo della quantità di moto del fluido contenuto nel volume di controllo, sommato al flusso netto di quantità di moto attraversante la superfice di confine, uguaglia la forza risultante delle forze esterne agenti sul fluido contenuto nel volume stesso.*

Per ogni cella adiacente a $(i,j)$ abbiamo che il flusso netto di quantità di moto entrante è

```math
\vec \Phi^q_{ab}(i,j) = - \left[
    \vec q'_{ab} (\vec u'_{ab}(i,j) \cdot \vec n_{ab}) + \vec q'_{22}(i,j) (\vec u'_{22}(i,j) \cdot \vec n_{ab})
\right] S_{ab}
```

Sulla superfice $k$ agiscono le forze dovute alla pressione del fluido

```math
\vec P_{ab}(i,j) = (p'_{ab} - p'_{22}) \vec n_{ab} S_{ab}
```

dove $p'_{ab}(i,j)$ è la pressione del fluido nella cella locale.

Per gli scopi di simulazione su piccola scala le forze gravitazionali possono essere trascurate.

Sempre sulle superifici di confine $k$ agiscono le forze di reazione:

```math
\vec R_{ab}(i,j)
```

Calcoliamo la variazione di quantità di moto senza le forze di reazione:

```math
\Delta \vec {\tilde {q'}}(i,j) = \sum_{ab}  (\vec \Phi^q_{ab}(i,j) + \vec P_{ab}(i,j)) \Delta t \\
```

finalmente

```math
\Delta \vec q(i,j) =
    \Delta \vec {\tilde {q'}}(i,j) + \sum_{ab} \vec R_{ab}(i,j) \Delta t\\

\Delta \vec q(i,j) = \sum_{ab}  (\vec \Phi^q_{ab}(i,j) + \vec P_{ab}(i,j)) + \vec R_{ab}(i,j) )\Delta t
```

## Principio di conservazione dell'energia

Il principio di conservazione dell'energia dice

> *la variazione di energia totale nell'unità di tempo del fluido contenuto nel volume di controllo  sommata al flusso netto di energia totale attraverso le facce del volume di controllo uguaglia la somma della potenza delle forze agenti sull'elemento di fluido.*

Il flusso netto di energia entrante dalla superfice $k$ è dato da

```math
\Phi^E_{ab}(i,j) = -(E'_{ab}(i,j)  \vec u_{ab}(i,j) + E_{22}(i,j) \vec u_{22}(i,j)) \cdot \vec n_{ab} S_{ab}
```

le potenze delle forze di pressione sono

```math
\Pi_{ab}(i,j) = \vec P_{ab}(i,j) \cdot \vec u'_{ab} + \vec P_{ab}(i,j) \cdot \vec u'_{22}(i,j) \\
\Pi_{ab}(i,j) = \vec P_{ab}(i,j) \cdot (\vec u'_{ab} + \vec u'_{22}(i,j))
```

le potenze delle forze di reazione sono

```math
\Rho_{ab}(i,j) = \vec R_{ab}(i,j) \cdot (\vec u'_{ab}(i,j) + \vec u'_{22}(i,j))
```

Quindi

```math
\Delta E(i,j) =
    \sum_{ab} (\Phi^E_{ab}(i,j) + \Pi_{ab}(i,j) + \Rho_{ab}(i,j))
\Delta t \\
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

Vediamo ora l'effetto di una cella solida, ovvero una cella immobile, con densità costante ed capacità terminca nulla (nessun scambio di calore con l'esterno).

```math
\frac{dm_{ij}}{dt} = 0 \\
\vec q_{ij} = \vec 0 \\
\frac{d E_{ij}}{dt} = 0
```

Possiamo includere nell'equazione di continuità (principio di conservazione della massa)
l'effetto del vincolo quindi definendo una matrice tale che

$ \mu_{ij} = 1 \Rightarrow $ cella vincolata
$ \mu_{ij} = 0 \Rightarrow $  cella non vincolata

### Prima equazione

Per il principio di conservazione della massa abbiamo

```math
\Delta m_{ij} =-(1 - \mu_{ij}) \sum_{ab} (\vec q'_{ab}(i,j) + \vec q'_{22}(i,j)) \cdot \vec n_{ab} S_k \Delta t
```

### Seconda equazione

Calcoliamo le sole forze di pressione delle celle adiacenti non vincolate

```math
\vec P_{ab}(i,j) = (1-\mu'_{ab}) (p'_{ab} - p'_{22}) \vec n_{ab} S_{ab}
```

e calcoliamo la variazione di quantità di moto senza le forze di reazione

```math
\Delta \vec {\tilde {q'}}(i,j) = \sum_{ab}  (\vec \Phi^q_{ab}(i,j) + \vec P_{ab}(i,j)) \Delta t \\
```

Prendiamo ora come riferimento una cella adiacente $(i,j)$ tale che|$(i_k, j_k)$ è una cella vincolata e posto che $ S_{ab}$ sia la superfice locale corrispondente a $S_k$.

Il vincolo impone che il flusso di massa uscente dalla cella $(i,j)$ attraverso la superfice di contatto $k$ sia nullo quindi deve essere sempre

```math
    \vec q(i,j) \cdot \vec n_{ab} \le 0
```

per cui

```math
(\vec q(i,j) + \Delta \vec q(i,j))  \cdot \vec n_{ab} \le 0 \\
```

Se

```math
(\vec q(i,j) + \Delta \vec {\tilde {q'}}(i,j) ) \cdot \vec n_{ab} > 0
```

la superficie vincolata reagisce con un forza $ R_{ab} \cdot \vec n_{ab} $ tale da contrastare la variazione di quantità di moto, quindi

```math
(\vec q(i,j) + \Delta \vec q(i,j)) \cdot \vec n_{ab} = 0 \\

\left(
\vec q_{22}(i,j) + \Delta \vec {\tilde {q'}}(i,j)
+ \sum_{cd}
    R_{cd}(i,j) \cdot \vec n_{cd} \Delta t
\right) \cdot \vec n_{ab} = 0\\

\sum_{cd} R_{cd}(i,j) \vec n_{cd} \cdot \vec n_{ab} = - \frac{1}{\Delta t}
\left(
    \vec q_{22}(i,j) + \Delta \vec {\tilde {q'}}(i,j)
\right) \cdot \vec n_{ab} \\
```

Siano $ (a_k, b_k) $ le $n$ superfici vincolate tali che

```math
(\vec q(i,j) + \Delta \vec {\tilde {q'}}(i,j) ) \cdot \vec n_{a_k b_k} > 0
```

possiamo scrivere allora $n$ equazioni con $ n $ incognite.

```math
\sum_l^n R_{a_l b_l}(i,j) \vec n_{a_l b_l} \cdot \vec n_{a_k b_k} = - \frac{1}{\Delta t}
\left(
    \vec q_{22}(i,j) + \Delta \vec {\tilde {q'}}(i,j)
\right) \cdot \vec n_{a_k b_k} \\
```

Ovvero

```math
    M U = V \\
   U = M^{-1} V
```

dove

```math
U = u_k = R_{a_k b_k}(i,j) \\
M = m_{ij} = \vec n_{a_i b_i} \cdot \vec n_{a_j b_j} \\
V = v_k = - \frac{1}{\Delta t}
\left(
    \vec q_{22}(i,j) + \Delta \vec {\tilde {q'}}(i,j)
\right) \cdot \vec n_{a_k b_k}
```

Mentre per le rimanenti superfici abbiamo

```math
R_{ab} = 0
```

### Terza equazione

Per quanto concerne il principio di conservazione dell'energia abbiamo che il flusso di energia è dato da

```math
\Phi^E_{ab}(i,j) =-(1 - \mu_{ij}) (E'_{ab}(i,j) \vec u'_{ab}(i,j)+ E'_{22}(i,j) \vec u'_{22}(i,j)) \cdot \vec n_{ab} S_k
```

Le potenze delle forze di pressione sono

```math
    \Pi_{ab}(i,j) = (1-\mu'_{ab}) \vec P_{ab}(i,j) \cdot (\vec u'_{ab} + u'_{22}(i,j))
```

Le potenze delle forze di reazione sono

```math
    \Rho_{ab}(i,j) = R_{ab} \vec n_{ab} \cdot (\vec u'_{ab}(i,j) + u'_{22}(i,j))
```

La variazione di energia risultante è

```math
    \Delta E(i,j) = \sum_{ab} [\Phi^E_{ab}(i,j) + \Pi_{ab}(i,j) + \Rho_{ab}(i,j)] \Delta t
```
