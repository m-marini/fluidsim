# Fluid

[TOC]

## Abstract

Simulates the dynamics of a fluid in a two-dimensional section.

## State tensor

The state of environment is represented by two-dimensional contiguous cells.
Each cell has several scalar properties (E.g. density, velocity vector comonents).
The environment is represented by a multidimension tensor $ C_{n \times m \times h} $ where $ n, m $ are the space size and $ h $ is the number of properties for each cell.

To compute the next state of the environment a convolution function is used.

The state tensor $ C $ is divided into $P_{3 \times 3 \times h}(i,j) $ tensors for each cell and the resulting state is computed applyng the convolution function $ F_{n \times m \times h \times 3 \times 3  \times h}(t, dt) $ to each tensor $P$ and a tensor $ G_{n \times m \times h}(t, dt) $

```math
    C_{ijk}(t + dt) = \sum_{a,b,c} F_{ijkabc}(t, dt) P_{abc}(i,j) + G_{ijk}(t, dt) \\
    a,b = 1 \dots 3, c = 1 \dots h
```

## Previous functions


## Cell functions

The general function is

```math
\gamma_{ij}(t + dt) = \sum_{k} \rho_{ijk} A_i(t, dt) + \gamma_{ij}(t) B(t, dt) + C(t, dt)
```

$k$ is the possible directions

### Conservative

```math
\gamma_{ij}(t + dt) = \sum_{k} \rho_{ijk} dt + \gamma_{ij}(t)
```

### Constant

```math
\gamma_{ij}(t + dt) = k
```

### Diffusion

```math
\gamma_{ij}(t + dt) = \sum_{k} \rho_{ijk} \alpha
```

### Elastic

```math
\gamma_{ij}(t + dt) = \sum_{k} \rho_{ijk} \alpha dt + \gamma_{ij}(t)
```

### Fluid

```math
\gamma_{ij}(t + dt) = \sum_{k} \rho_{ijk} \alpha dt + \gamma_{ij}(t) (1 - \beta dt)
```

### Sin

```math
\gamma_{ij}(t + dt) = \frac{V}{2} \left( 1 + \sin(\omega t) \right)
```

## Relation function
