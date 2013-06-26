/*
 * UniverseImpl.java
 *
 * $Id: UniverseImpl.java,v 1.5 2007/08/18 08:29:54 marco Exp $
 *
 * 02/ago/07
 *
 * Copyright notice
 */
package org.mmarini.fluid.model;

import java.awt.Dimension;

/**
 * 
 * The concrete implementation of the Universe.
 * <p>
 * The implementation has the array of cells mantaining a DoubleBufferedDouble,
 * the structure of relations between the cells, the size of the universe, the
 * contraints of the cells and relations.
 * </p>
 * <p>
 * The structure of relations contain three arrays of DoubleBufferedDouble.<br>
 * The first array contains the horizontal relations between cell (i,j) and (i,j +
 * 1). It has h * (w - 1) relations. <br>
 * 
 * The second array contains the lateral relations between cell (i,j) and (i +
 * 1,j) and has (h - 1) * w relations. <br>
 * 
 * The third array contains the lateral relations between cell (i,j) and (i +
 * 1,j - 1) for even i and (i + 1, j + 1) for odd i. It has (h - 1) * (w - 1)
 * relations.
 * </p>
 * 
 * @author US00852
 * @version $Id: UniverseImpl.java,v 1.5 2007/08/18 08:29:54 marco Exp $
 */

public class UniverseImpl implements Universe, FluidConstants {

    private Cell[][] cell;

    private Relation[][][] relation;

    private Dimension size;

    private CellUpdateContext cellContext;

    private RelationUpdateContext relationContext;

    private TimeContext timeContext;

    /**
         * @see org.mmarini.fluid.model.Universe#simulate(double)
         */
    public void simulate(double time) {
	getTimeContext().update(time);
	updateRelations();
	updateCells();
	swapRelations();
	swapCells();
    }

    /**
         * Swaps the cell values.
         */
    private void swapCells() {
	Cell[][] cell = getCell();
	int n = cell.length;
	for (int i = 0; i < n; ++i) {
	    int m = cell[i].length;
	    for (int j = 0; j < m; ++j) {
		cell[i][j].getValue().swap();
	    }
	}
    }

    /**
         * Swaps the relation values.
         */
    private void swapRelations() {
	Relation[][][] rel = getRelation();
	int l = rel.length;
	for (int i = 0; i < l; ++i) {
	    if (rel[i] != null) {
		int n = rel[i].length;
		for (int j = 0; j < n; ++j) {
		    if (rel[i][j] != null) {
			int m = rel[i][j].length;
			for (int k = 0; k < m; ++k) {
			    Relation relation2 = rel[i][j][k];
			    if (relation2 != null)
				relation2.getValue().swap();
			}
		    }
		}
	    }
	}
    }

    /**
         * Update the relation values.
         */
    private void updateRelations() {
	RelationUpdateContext ctx = getRelationContext();
	DoubleBufferedDouble[] adjacent = ctx.getAdjacents();
	Cell[][] cell = getCell();
	Relation[][][] relation = getRelation();

	/*
         * Horizontal relations
         */
	Relation[][] relation1 = relation[0];
	Dimension size = getSize();
	int h = size.height;
	int w = size.width;
	for (int i = 0; i < h; ++i) {
	    for (int j = 0; j < w - 1; ++j) {
		/*
                 * update context
                 */
		Relation rel = relation1[i][j];
		ctx.setSubject(rel.getValue());
		ctx.setFunction(rel.getFunction());
		adjacent[0] = cell[i][j].getValue();
		adjacent[1] = cell[i][j + 1].getValue();
		updateRelation(ctx);
	    }
	}

	/*
         * Second relations
         */
	relation1 = relation[1];
	for (int i = 0; i < h - 1; i += 2) {
	    for (int j = 0; j < w; ++j) {
		/*
                 * update context
                 */
		Relation rel = relation1[i][j];
		ctx.setSubject(rel.getValue());
		ctx.setFunction(rel.getFunction());
		adjacent[0] = cell[i][j].getValue();
		adjacent[1] = cell[i + 1][j].getValue();
		updateRelation(ctx);
	    }
	}
	for (int i = 1; i < h - 1; i += 2) {
	    for (int j = 0; j < w - 1; ++j) {
		/*
                 * update context
                 */
		Relation rel = relation1[i][j];
		ctx.setSubject(rel.getValue());
		ctx.setFunction(rel.getFunction());
		adjacent[0] = cell[i][j].getValue();
		adjacent[1] = cell[i + 1][j + 1].getValue();
		updateRelation(ctx);
	    }
	}

	/*
         * Third relations
         */
	relation1 = relation[2];
	for (int i = 0; i < h - 1; i += 2) {
	    for (int j = 1; j < w; ++j) {
		/*
                 * update context
                 */
		Relation rel = relation1[i][j];
		ctx.setSubject(rel.getValue());
		ctx.setFunction(rel.getFunction());
		adjacent[0] = cell[i][j].getValue();
		adjacent[1] = cell[i + 1][j - 1].getValue();
		updateRelation(ctx);
	    }
	}
	for (int i = 1; i < h - 1; i += 2) {
	    for (int j = 0; j < w - 1; ++j) {
		/*
                 * update context
                 */
		Relation rel = relation1[i][j];
		ctx.setSubject(rel.getValue());
		ctx.setFunction(rel.getFunction());
		adjacent[0] = cell[i][j].getValue();
		adjacent[1] = cell[i + 1][j].getValue();
		updateRelation(ctx);
	    }
	}
    }

    /**
         * Update the relation value between two cell.
         * <p>
         * The relation next value is the outbound scalar value of the subject
         * cell.
         * </p>
         * 
         * @param context
         *                the relation update context
         */
    private void updateRelation(RelationUpdateContext context) {
	DoubleBufferedDouble sub = context.getSubject();
	DoubleBufferedDouble[] rel = context.getAdjacents();
	int n = rel.length;
	double v = 0;
	for (int i = 0; i < n; ++i) {
	    v += context.getA(i) * rel[i].getValue();
	}
	v += context.getB() * sub.getValue() + context.getC() + 0;
	sub.setNextValue(v);
    }

    /**
         * Updates the cell values.
         */
    private void updateCells() {
	CellUpdateContext ctx = getCellContext();
	DoubleBufferedDouble[] rel = ctx.getAdjacents();
	Cell[][] cells = getCell();
	Dimension size = getSize();
	int h = size.height;
	int w = size.width;
	for (int i = 0; i < h; ++i) {
	    for (int j = 0; j < w; ++j) {
		Cell cell = cells[i][j];
		/*
                 * update context
                 */
		ctx.setSubject(cell.getValue());
		ctx.setFunction(cell.getFunction());
		createAdjacentRelation(rel, i, j);
		updateCell(ctx);
	    }
	}
    }

    /**
         * Update the cell value depending on the adjacent relations.
         * <p>
         * The adjacent relations is inbound the subject cell for even indexes
         * and outbound the subject for odd indexes.
         * </p>
         * 
         * @param context
         *                the cell update context
         */
    private void updateCell(CellUpdateContext context) {
	DoubleBufferedDouble sub = context.getSubject();
	DoubleBufferedDouble[] rel = context.getAdjacents();
	int n = rel.length;
	double v = 0;
	for (int i = 0; i < n; ++i) {
	    DoubleBufferedDouble f = rel[i];
	    if (f != null)
		v += context.getA(i) * f.getNextValue();
	}
	v += context.getB() * sub.getValue() + context.getC();
	sub.setNextValue(v);
    }

    /**
         * Returns the value of a relation.
         * 
         * @param direction
         *                the direction of the relation
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @return the value of relation
         */
    public DoubleBufferedDouble getRelation(int direction, int i, int j) {
	Relation func = getRelationLocal(direction, i, j);
	if (func == null)
	    return null;
	return func.getValue();
    }

    /**
         * Return a relation.
         * 
         * @param direction
         *                the direction of relation
         * @param i
         *                the row index
         * @param j
         *                the column index
         * @return the relation
         */
    private Relation getRelationLocal(int direction, int i, int j) {
	Dimension size = getSize();
	int w = size.width;
	int h = size.height;
	Relation[][][] relation = getRelation();
	switch (direction) {
	case LEFT:
	    if (j > 0) {
		return relation[RIGHT][i][j - 1];
	    }
	    break;
	case RIGHT:
	    if (j < w - 1) {
		return relation[RIGHT][i][j];
	    }
	    break;
	case UP_RIGHT:
	    if (i < h - 1)
		return relation[UP_RIGHT][i][j];
	    break;
	case UP_LEFT:
	    if (i < h - 1)
		return relation[UP_LEFT][i][j];
	    break;
	case DOWN_LEFT:
	    if (i <= 0)
		return null;
	    if ((i % 2) != 0)
		return relation[UP_RIGHT][i - 1][j];
	    if (j > 0)
		return relation[UP_RIGHT][i - 1][j - 1];
	    break;
	case DOWN_RIGHT:
	    if (i <= 0)
		return null;
	    if ((i % 2) == 0)
		return relation[UP_LEFT][i - 1][j];
	    if (j < w - 1)
		return relation[UP_LEFT][i - 1][j + 1];
	    break;
	}
	return null;
    }

    /**
         * Creates the cell adjacent relations.
         * <p>
         * It values the rel parameter with the adjacent relations
         * 
         * @param rel
         *                the adjacent relations
         * @param i
         *                the row index
         * @param j
         *                the column index
         */
    private void createAdjacentRelation(DoubleBufferedDouble[] rel, int i, int j) {
	for (int k = 0; k < rel.length; ++k) {
	    rel[k] = getRelation(k, i, j);
	}
    }

    /**
         * @see Universe#getCell(int, int)
         */
    public DoubleBufferedDouble getCell(int i, int j) {
	return getCell()[i][j].getValue();
    }

    /**
         * @see Universe#getSize()
         */
    public Dimension getSize() {
	return size;
    }

    /**
         * Sets the size of universe.
         * 
         * @param size
         *                the size to set
         */
    public void setSize(Dimension size) {
	this.size = size;
	init();
    }

    /**
         * Initializes the universe.
         * <p>
         * It cretates the cell and relation array.
         * </p>
         */
    private void init() {
	Dimension s = getSize();
	int w = s.width;
	int h = s.height;
	Cell[][] cells = new Cell[h][w];
	for (int i = 0; i < h; ++i) {
	    for (int j = 0; j < w; ++j) {
		cells[i][j] = new Cell();
	    }
	}
	setCell(cells);

	Relation[][][] relas = new Relation[RELATION_DIRECTIONS][h][w];
	Relation[][] rels = relas[0];
	for (int i = 0; i < h; ++i) {
	    for (int j = 0; j < w - 1; ++j) {
		rels[i][j] = new Relation();
	    }
	}

	rels = relas[1];
	for (int i = 0; i < h - 1; i += 2) {
	    for (int j = 0; j < w; ++j) {
		rels[i][j] = new Relation();
	    }
	}
	for (int i = 1; i < h - 1; i += 2) {
	    for (int j = 0; j < w - 1; ++j) {
		rels[i][j] = new Relation();
	    }
	}

	rels = relas[2];
	for (int i = 0; i < h - 1; i += 2) {
	    for (int j = 1; j < w; ++j) {
		rels[i][j] = new Relation();
	    }
	}
	for (int i = 1; i < h - 1; i += 2) {
	    for (int j = 0; j < w; ++j) {
		rels[i][j] = new Relation();
	    }
	}

	setRelation(relas);
    }

    /**
         * Returns the cell array.
         * 
         * @return the cell
         */
    private Cell[][] getCell() {
	return cell;
    }

    /**
         * Sets the ecll array.
         * 
         * @param cell
         *                the cell to set
         */
    private void setCell(Cell[][] cell) {
	this.cell = cell;
    }

    /**
         * Returns the relation structure between adjacent cells.
         * 
         * @return the horizontalRelation
         */
    private Relation[][][] getRelation() {
	return relation;
    }

    /**
         * Sets the relation structure between adjacent cells.
         * 
         * @param relation
         *                the relation
         */
    private void setRelation(Relation[][][] relation) {
	this.relation = relation;
    }

    /**
         * Returns the cell update context.
         * 
         * @return the cellContext
         */
    private CellUpdateContext getCellContext() {
	return cellContext;
    }

    /**
         * Returns the time context.
         * 
         * @return the timeContext
         */
    private TimeContext getTimeContext() {
	return timeContext;
    }

    /**
         * Returns the relation array.
         * 
         * @return the relationContext
         */
    private RelationUpdateContext getRelationContext() {
	return relationContext;
    }

    /**
         * Sets the relation update context.
         * <p>
         * The context is used to calculate the next relation values during the
         * simulation.
         * </p>
         * 
         * @param relationContext
         *                the relationContext to set
         */
    public void setRelationContext(RelationUpdateContext relationContext) {
	this.relationContext = relationContext;
    }

    /**
         * Sets the cell update context.
         * <p>
         * The context is used to calculate the next cell values during the
         * simulation.
         * </p>
         * 
         * @param cellContext
         *                the cellContext to set
         */
    public void setCellContext(CellUpdateContext cellContext) {
	this.cellContext = cellContext;
    }

    /**
         * @param timeContext
         *                the timeContext to set
         */
    public void setTimeContext(TimeContext timeContext) {
	this.timeContext = timeContext;
    }

    /**
         * @see Universe#setCellFunction(int, int, CellFunction)
         */
    public void setCellFunction(int i, int j, CellFunction function) {
	getCell()[i][j].setFunction(function);
    }

    /**
         * @see Universe#setRelationFunction(int, int, RelationFunction)
         */
    public void setRelationFunction(int i, int j, RelationFunction function) {
	for (int k = 0; k < RELATION_DIRECTIONS * 2; ++k) {
	    setRelationFunction(k, i, j, function);
	}
    }

    /**
         * @see Universe#setRelationFunction(int, int, int, RelationFunction)
         */
    public void setRelationFunction(int direction, int i, int j,
	    RelationFunction function) {
	Relation rel = getRelationLocal(direction, i, j);
	if (rel != null)
	    rel.setFunction(function);
    }
}
