# Equazioni di eulero

[TOC]

## Abstract

Le equazioni di Eulero sono:

```math
    \frac{\partial \rho}{\partial t} + \nabla \cdot \rho \vec u = 0 \\
    \frac{\partial \rho \vec u}{\partial t} + \nabla \cdot (\rho \vec u \otimes \vec u + p I) = 0 \\
    \frac{\partial E}{\partial t} + \nabla \cdot \vec u (E + p) = 0
```

### Conservazione della massa

Dalla prima equazione per integrazione possiamo derivare

```math
    d \rho = -\int_V \nabla \cdot \rho \vec u \, dV \, dt
```

per $ V $ volume infinitesimale

e dal teorima della divergenza diventa

```math
    d \rho = -\oint_S \rho \vec u \cdot \vec n \, ds \, dt
```

per $ S $ la superficie di confine del volume infinitesimale e $ \vec n $ la normale uscente dal volume.

### Conservazione della quantità di moto

Dalla seconda equazione per integrazione possiamo derivare

```math
    d \int_V \rho \vec u \, dV = -\int_V \nabla \cdot (\rho \vec u \otimes \vec u + p I) \, dV \, dt \\
    d \int_V \rho \vec u \, dV = -\oint_S [(\rho \vec u) \vec u \cdot \vec n + p \vec n] \, ds \, dt
```

### Conservazione dell'energia

Dalla terza equazione per integrazione possiamo derivare

```math
    d E = -\int_V \nabla \cdot \vec u (E + p) \, dV \, dt \\
    d E = -\oint_S  \vec n \cdot \vec u (E + p) \, ds \, dt
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

Quindi abbiamo che

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

### Proprietà del fluido

Il fluido contenuto nella cella $(i, j)$ è definito da tre proprietà di base:

- $ \rho_{ij} $ la densità del fluido
- $ \vec u_{ij} $ la velocità del fluido
- $ T_{ij} $ la temperatura del fluido

dalle prorietà di base deriviamo altre proprietà con le equazioni di stato

- $ m_{ij} = V \rho_{ij} $ la massa,
- $ \vec q_{ij} = V \rho_{ij} \vec u_{ij} $ la quantità di moto ($ \vec u_{ij} $ è la velocità del fluido)
- $ E_{ij} = \varsigma T_{ij} m_{ij} $ l'energia interna del fluido
- $ p_{ij} = \frac{m_{ij}}{V M_{mol}} T_{ij} R $ pressione

dove $ \varsigma $ è il calore specifico del fluido,
$ M_{mol} $ è la massa molecolare del fluido

Nel caso dell'aria in condizioni ISA abbiamo

```math
n = \frac{pV}{RT} = \frac{101 325 \cdot 1}{8.31446 \cdot 298} = 40,89 mol
```

la massa molecare dell'aria è di $28.96 \frac{g}{mol}$ quindi il peso dell'aria è di $ 40.89 \cdot 28.96 = 1184 g $
La densità dell'aria è di $ 1.184 \frac{Kg}{m^2} $

### Proprietà delle superfici di confine

Per ogni superficie di confine possiamo definire le seguenti proprietà come medie delle celle adiacenti

- $ \bar \rho = \frac{\rho + \rho_k}{2} $ densità media
- $ \bar {\vec u} = \frac{\vec u  + \vec u_k}{2} $ velocità media
- $ \bar {\vec q} = \frac{\vec q  + \vec q_k}{2} $ quantità di moto media
- $ \bar E = \frac{E + E_k}{2} $ energia media
- $ \vec \Phi^m = \bar \rho \bar {\vec u} S $ flusso di massa
- $ \Phi^q = \bar {\vec q} \otimes \bar {\vec u} $ tensore di flusso di quantità di moto

per le superfice isolanti invece

- $ \bar \rho = 0 $ densità media
- $ \bar {\vec u} = \vec 0 $ velocità media
- $ \bar {\vec q} = \vec 0$ quantità di moto media
- $ \bar E_k = 0 $ energia media
- $ \vec \Phi^m = \vec 0 $ flusso di massa
- $ \Phi^q = 0 $ tensore di flusso di quantità di moto

### Simulazione del flusso di massa

La variazione di massa di una cella

```math
    \Delta m = - \sum _k \vec n_k \cdot \vec \Phi^m \Delta t
```

### Simulazione del flusso di quantità di moto

La variazione di quantità di moto di una cella è quindi

```math
    \Delta \vec q = - \sum_k [\Phi^q + p I] \cdot \vec n_k S \Delta t
```

### Simulazione di flusso di energia

La variazione di energia è quindi

```math
    \Delta E = - \sum_k \vec n_k \cdot \bar {\vec u} (\bar E + p) S \Delta t
```

dalle variazioni di massa, quantita di moto ed energia si possono calcolare le variazioni delle proprietà di base

```math
    \Delta \rho_{ij} = \frac{\Delta m_{ij}}{V} \\
    \Delta \vec u_{ij} = \frac{\Delta \vec q_{ij}}{m_{ij}} \\
    \Delta T_{ij} = \frac{\Delta E_{ij}}{\varsigma m_{ij}}
```
