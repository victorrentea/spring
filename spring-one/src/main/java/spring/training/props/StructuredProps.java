package spring.training.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public class StructuredProps {
    private String a;
    private List<String> list;
    private Map<String,String> map;

}
