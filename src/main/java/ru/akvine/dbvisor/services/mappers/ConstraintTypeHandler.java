package ru.akvine.dbvisor.services.mappers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import ru.akvine.compozit.commons.visor.ConstraintType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(ConstraintType.class)
public class ConstraintTypeHandler implements TypeHandler<ConstraintType> {
    @Override
    public void setParameter(PreparedStatement ps, int i, ConstraintType parameter, JdbcType jdbcType) {
        String errorMessage = String.format(
                "Calling %s setParameter() method is not supported!",
                ConstraintTypeHandler.class.getSimpleName()
        );
        throw new UnsupportedOperationException(errorMessage);
    }

    @Override
    public ConstraintType getResult(ResultSet resultSet, String columnName) throws SQLException {
        return ConstraintType.fromCode(resultSet.getString(columnName));
    }

    @Override
    public ConstraintType getResult(ResultSet rs, int columnIndex) throws SQLException {
        return ConstraintType.fromCode(rs.getString(columnIndex));
    }

    @Override
    public ConstraintType getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ConstraintType.fromCode(cs.getString(columnIndex));
    }
}
