/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.types.expr;

import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.operation.OBoolean;
import com.mysema.query.types.operation.ONumber;
import com.mysema.query.types.operation.OString;
import com.mysema.query.types.operation.Ops;


/**
 * EComparable represents comparable expressions
 * 
 * @author tiwe
 * 
 * @param <D> Java type
 * @see java.lang.Comparable
 */
@SuppressWarnings("unchecked")
public abstract class EComparable<D extends Comparable> extends Expr<D> {

    private OrderSpecifier<D> asc;
    
    private OrderSpecifier<D> desc;
    
    private EString stringCast;

    public EComparable(Class<? extends D> type) {
        super(type);
    }
    
    @Deprecated
    public EBoolean after(D right) {
        return gt(right);
    }    

    @Deprecated
    public EBoolean after(Expr<D> right) {
        return gt(right);
    }
    
    @Deprecated
    public EBoolean aoe(D right) {
        return goe(right);
    }    

    @Deprecated
    public EBoolean aoe(Expr<D> right) {
        return goe(right);
    }
    
    @Deprecated
    public EBoolean before(D right) {
        return lt(right);
    }    

    @Deprecated
    public EBoolean before(Expr<D> right) {
        return lt(right);
    }
    
    @Deprecated
    public EBoolean boe(D right) {
        return loe(right);
    }    

    @Deprecated
    public EBoolean boe(Expr<D> right) {
        return loe(right);
    }

    /**
     * Create a <code>this &gt; right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public EBoolean gt(D right) {
        return gt(ExprConst.create(right));
    }
    
    /**
     * Create a <code>this &gt; right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public EBoolean gt(Expr<D> right) {
        return OBoolean.create(Ops.AFTER, this, right);
    }

    /**
     * Create a <code>this &gt;= right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public EBoolean goe(D right) {
        return goe(ExprConst.create(right));
    }

    /**
     * Create a <code>this &gt;= right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public EBoolean goe(Expr<D> right) {
        return OBoolean.create(Ops.AOE, this, right);
    }

    /**
     * Get an OrderSpecifier for ascending order of this expression
     * 
     * @return
     */
    public final OrderSpecifier<D> asc() {
        if (asc == null){
            asc = new OrderSpecifier<D>(Order.ASC, this);
        }            
        return asc;
    }

    /**
     * Create a <code>this &lt; right</code> expression 
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final EBoolean lt(D right) {
        return lt(ExprConst.create(right));
    }

    /**
     * Create a <code>this &lt; right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final EBoolean lt(Expr<D> right) {
        return OBoolean.create(Ops.BEFORE, this, right);
    }

    /**
     * Create a <code>this &lt;= right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final EBoolean loe(D right) {
        return OBoolean.create(Ops.BOE, this, ExprConst.create(right));
    }

    /**
     * Create a <code>this &lt; right</code> expression
     * 
     * @param right rhs of the comparison
     * @return
     * @see java.lang.Comparable#compareTo(Object)
     */
    public final EBoolean loe(Expr<D> right) {
        return OBoolean.create(Ops.BOE, this, right);
    }

    /**
     * Create a <code>first &lt; this &lt; second</code> expression
     * 
     * @param first
     * @param second
     * @return
     */
    public final EBoolean between(D first, D second) {
        return OBoolean.create(Ops.BETWEEN, this, ExprConst.create(first), ExprConst.create(second));
    }

    /**
     * Create a <code>first &lt; this &lt; second</code> expression
     * 
     * @param first
     * @param second
     * @return
     */
    public final EBoolean between(Expr<D> first, Expr<D> second) {
        return OBoolean.create(Ops.BETWEEN, this, first, second);
    }

    
    /**
     * Create a cast expression to the given numeric type
     * 
     * @param <A>
     * @param type
     * @return
     */
    public <A extends Number & Comparable<? super A>> ENumber<A> castToNum(Class<A> type) {
        return ONumber.create(type, Ops.NUMCAST, this, ExprConst.create(type));
    }

    /**
     * Get an OrderSpecifier for descending order of this expression
     * 
     * @return
     */
    public final OrderSpecifier<D> desc() {
        if (desc == null){
            desc = new OrderSpecifier<D>(Order.DESC, this);
        }            
        return desc;
    }

    /**
     * @param first
     * @param second
     * @return
     */
    public final EBoolean notBetween(D first, D second) {
        return between(first, second).not();
    }

    /**
     * @param first
     * @param second
     * @return
     */
    public final EBoolean notBetween(Expr<D> first, Expr<D> second) {
        return between(first, second).not();
    }

    /**
     * Get a cast to String expression 
     * 
     * @see     java.lang.Object#toString()
     * @return
     */
    public EString stringValue() {
        if (stringCast == null){
            stringCast = OString.create(Ops.STRING_CAST, this);
        }            
        return stringCast;
    }

}