package com.youngbook.common;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

/**
 * Created by leevits on 4/15/2018.
 */
public interface IXmlable {

    public String toXmlString() throws Exception;
    public String toXmlString(String encoding) throws Exception;

    public List<Element> toXmlNodes() throws Exception;

}
