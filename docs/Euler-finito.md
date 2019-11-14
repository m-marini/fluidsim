# Equazioni di Eulero

[TOC]

## Abstract

Le equazioni di Eulero sono:

```math
    \frac{\partial \rho}{\partial t} + \nabla \cdot \rho \vec u = 0 \\
    \frac{\partial \rho \vec u}{\partial t} + \nabla \cdot (\rho \vec u \otimes \vec u + p I) = 0 \\
    \frac{\partial E}{\partial t} + \nabla \cdot \vec u (E + p) = 0
```

### Conservazione della massa

Dalla prima equazione per integrazione otteniamo

```math
    d  \int_V \rho dV = - \int_V \nabla \cdot \rho \vec u \, dV \, dt
```

per $ V $ volume infinitesimale e dal teorima della divergenza diventa

```math
    d \int_V \rho dV = -\oint_S \rho \vec u \cdot \vec n \, ds \, dt
```

per $ S $ la superficie di confine del volume infinitesimale e $ \vec n $ la normale uscente dal volume.

### Conservazione della quantità di moto

Dalla seconda equazione per integrazione otteniamo (da verificare)

```math
    d \int_V \rho \vec u \, dV = -\int_V \nabla \cdot (\rho \vec u \otimes \vec u + p I) \, dV \, dt \\
```

### Conservazione dell'energia

Dalla terza equazione per integrazione possiamo derivare

```math
    d \int_V E dV = -\int_V \nabla \cdot \vec u (E + p) \, dV \, dt \\
    d \int_V E dV = -\oint_S  \vec n \cdot \vec u (E + p) \, ds \, dt
```

## Condizioni a contorno

Le condizioni a contorno definiscono dei vincoli alle equazioni nei punti di confine.

### Superficie isolante

Un possibile vincolo è una superficie isolante che impedisce il passaggio di massa, di quantità di moto e di energia.
Questa superficie definisce una zona di discontinuità della densità e dell'energia mentre la velocità lungo la normale alla superficie deve essere nulla.

```math
    \rho_k =  \rho \\
    \vec u_k \cdot \vec n = 0 \\
    E_k = E
```

La superfice reagisce anche alla pressione del fluido con una forza uguale e contraria per cui

```math
    \frac{\partial}{\partial t} \oint_{S_k} \rho \vec u \cdot \vec n \, ds = 0 \\
    \frac{\partial}{\partial t} \oint_{S_k} [(\rho \vec u) \vec u \cdot \vec n] \, ds \, dt = \vec 0 \\
    \frac{\partial}{\partial t} \oint_{S_k} \vec n \cdot \vec u E \, ds = 0 \\
```

### Generatore di flusso

Un secondo vincolo è una superficie che genera un flusso costante di fluido.

Caratteristiche di questa superficie sono

- $ \vec U $ velocità e direzione del flusso
- $ \Rho $ densità del flusso generato
- $ E = \varsigma T \Rho V$ energia del flusso generato

quindi abbiamo

```math
    d \rho_k = \frac{\partial}{\partial t} \oint_{S_k} \Rho \vec U \cdot \vec n \, ds = 0 \\
    d \int_V \rho \vec u \, dV = -\oint_S (\Rho \vec U) \vec U \cdot \vec n \, ds \, dt \\
    d E = -\oint_S  \vec n \cdot \vec U E \, ds \, dt
```

## Discretizzazione

### Reticolo a volumi finiti

Prendiamo in considerazione un modello approssimato costituito da un reticolo bidimensionale di $ m $ righe orizzontali per $ m $ colonne verticali di celle quadrate di lato $ \Delta s$.

```text
+---+---+---+
|   |   |   |
+---+---+---+
|   |   |   |
+---+---+---+
|   |   |   |
+---+---+---+
```

Possiamo identificare ogni cella con una coppia di indici $ (i,j) $ rispettivamente per la riga e la colonna.

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

### Superfici finite di confine

Le superfici di confine possiamo rappresentarle con con una matrice di $ n \times (m+1) $ superfici verticali

```text
|   |   |   |

|   |   |   |

|   |   |   |
```

Per una cella $ (i,j) $ abbiamo due superfici verticali identificate dagli indici $ (i, j) $ e $ (i, j + 1) $.

e una seconda matrice di $ (n+1) \times m $ superfici orizzontali

```text
--- --- ---

--- --- ---

--- --- ---

--- --- ---
```

Per una cella $ (i,j) $ abbiamo due superfici orizzontali identificate dagli indici $ (i, j) $ e $ (i + 1, j) $.

Le variazioni di pressione e densità del fluido viaggiano alla velocità del suono. Per simulare adeguatamente il fluido è necessario che la distanza percorsa da una variazione di pressine o densità nell'intervallo di tempo simulato sia inferiore alla dimensione della cella quindi:

```math
    \Delta s < a \Delta t
```


### Proprietà del fluido

Per semplificare il modello consideriamo solo processi adiabatici (senza scambio di calore) e quindi un fluido a temperatura costante $ T $

Il fluido contenuto nella cella $(i, j)$ è definito da tre proprietà di base:

- $ \rho_{ij} $ la densità del fluido
- $ \vec u_{ij} $ la velocità del fluido
- $ E_{ij} $ densità di energia del fluido

dalle prorietà di base deriviamo altre proprietà con le equazioni di stato

- $ m_{ij} = V \rho_{ij} $ la massa,
- $ E_{ij} = ? $ l'energia interna del fluido
- $ p_{ij} = \frac{\rho_{ij}}{M_{mol}} T_{ij} R $ pressione

$ M_{mol} $ è la massa molecolare del fluido

#### Aria

Nel caso dell'aria in condizioni ISA abbiamo

- pressione $ 101325 Pa (\frac{Kg}{m s^2})$
- temperatura $ 298 K $
- la massa molecare dell'aria è di $28.96 \frac{g}{mol}$
- il calore specifico è di $ 1004.3 \frac{J}{Kg \, K} (\frac{m^2}{s^2 K}) $

il numero di moli di un metro cubo d'aria è di

```math
n = \frac{pV}{RT} = \frac{101 325 \cdot 1}{8.31446 \cdot 298} = 40,89 \, mol
```

- il peso di un metro cubo di aria è di $ 40.89 \cdot 28.96 = 1184 g $
- la densità dell'aria è di $ 1.184 \frac{Kg}{m^3} $
- l'indice adiabatico è $\gamma = 1.4$
- la velocità del suono è $ a = \sqrt{\gamma T \frac{R}{Mmol}} = 346,1 \frac{m}{s} $
- potenza di un flusso alla velocità di $1 \frac{m}{s}$ su una superfice di $1 m^2$ =  $ 101325 \,W (\frac{Kg \, m^2}{s^3}) $


### Proprietà delle superfici di confine

Per ogni superficie di confine $ k $ possiamo definire le seguenti proprietà come medie delle celle adiacenti

- $ \mu_{kij} $ flag se superficie non isolante
- $ \rho_{kij} = \mu_{kij} \frac{\rho_{ij} + \rho_{i_k j_k}}{2} $ densità media
- $ {\vec u_{kij}} = \mu_{kij} \frac{\vec u_{ij}  + \vec u_{i_k j_k}}{2} $ velocità media
- $ E_{kij} = \mu_{kij} \frac{E_{ij} + E_{i_k j_k}}{2} $ energia media
- $ p_{kij} = \mu_{kij} \frac{p_{ij} + p_{i_k j_k}}{2} $ pressione media
- $ \vec \phi _{kij} = \rho_{kij} \vec u_{kij} $ flusso di densità

### Simulazione del flusso di massa

La variazione di massa di una cella

```math
    \Delta \int_V \rho_{ij}\, dV= - \sum _k \rho_{kij} \vec n_k \cdot \vec u_{kij} S \Delta t \\
    \Delta \rho_{ij} = - \frac{S \Delta t}{V} \sum _k \vec \phi_{kij} \cdot \vec n_k
```

### Simulazione del flusso di quantità di moto

La variazione di quantità di moto di una cella è quindi

```math
    \Delta \int_V (\rho_{ij} \vec u_{ij}) dV= - \sum_k [\rho_{kij} \vec u_{kij} (\vec u_{kij} \cdot \vec n_k) + p_{kij} \vec n_k] S \Delta t \\
    \Delta (\rho_{ij} \vec u_{ij}) = - \frac{1}{V} \sum_k [\vec \phi_{kij} (\vec u_{kij} \cdot \vec n_k) + p_{kij} \vec n_k] S \Delta t \\
    \Delta \vec u_{ij} = - \frac{S \Delta t}{V \rho_{ij}}
    \left[
        \sum_k \vec \phi_{kij} (\vec u_{kij} \cdot \vec n_k)
        + \sum_k p_{kij} \vec n_k
    \right]
```

### Simulazione di flusso di energia

La variazione di energia è quindi

```math
    \Delta \int_V  E_{ij} \, dV = - \sum_k \vec n_k \cdot \vec u_{kij} ( E_{kij} + p_{kij}) S \Delta t \\
    \Delta E_{ij} = - \frac{S \Delta t}{V}
    \left[
        \sum_k \vec n_k \cdot \vec u_{kij} E_{kij} + \sum_k \vec n_k \cdot \vec u_{kij}p_{kij}
    \right] \\
    \Delta T_{ij} = \frac{\Delta E_{ij}}{\varsigma \rho_{ij} V}
```
