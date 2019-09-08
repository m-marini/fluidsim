# Fluid

[TOC]

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
