package com.cegeka.project.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

public class YearMonthType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{"year", "month"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{IntegerType.INSTANCE, IntegerType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int property) throws HibernateException {
        if(component == null) {
            return null;
        }
        YearMonth yearMonth = (YearMonth) component;
        if(property == 0){
            return yearMonth.getYear();
        }else if(property == 1){
            return yearMonth.getMonth();
        }else{
            throw new HibernateException("Invalid property index [" + property + "]" );
        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
        System.out.println("set");
    }

    @Override
    public Class returnedClass() {
        return YearMonth.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if ( x == y )
            return true;
        if ( x == null || y == null )
            return false;
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        return YearMonth.of(rs.getInt(names[0]), rs.getInt(names[1]));
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        YearMonth yearMonth = (YearMonth) value;
        StandardBasicTypes.INTEGER.nullSafeSet(st, yearMonth.getYear(), index, session);
        StandardBasicTypes.INTEGER.nullSafeSet(st, yearMonth.getMonthValue(), index+1, session);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value, SharedSessionContractImplementor session) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return original;
    }
}
