package com.agent.czb.core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
public class XmlUtil {
    public static Document processXml(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public static Element xmlToElement(String xml) throws IOException, SAXException, ParserConfigurationException {
        Document document = processXml(xml);
        return document.getDocumentElement();
    }

    public static String xmlToTextContent(Element element) {
        return element.getTextContent();
    }

    public static String xmlToTextContent(org.w3c.dom.Node item) {
        return item.getTextContent();
    }

    public static String xmlToAttribute(org.w3c.dom.Node item, String attrName) {
        return item.getAttributes().getNamedItem(attrName).getNodeValue();
    }

    public static String xmlToAttribute(Element element, String attrName) {
        return element.getAttribute(attrName);
    }

    public static String xmlToNodeName(Element element) {
        return element.getNodeName();
    }

    public static Document createXML() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    public static Element createElement(Document document, String name) {
        return document.createElement(name);
    }

    public static Element createElement(Document document, String name, String text) {
        return createElement(document, name, text, null);
    }

    public static Element createElement(Document document, String name, String text, Map<String, String> attrs) {
        Element element = document.createElement(name);
        element.setTextContent(text);
        if (attrs != null) {
            for (String k : attrs.keySet()) {
                setAttribute(element, k, attrs.get(k));
            }
        }
        return element;
    }

    public static void setTextContent(Element element, String text) {
        element.setTextContent(text);
    }

    public static void setAttribute(Element element, String name, String attr) {
        element.setAttribute(name, attr);
    }

    public static void appendChild(Element element, Node... newChilds) {
        if (newChilds != null && newChilds.length > 0) {
            for (Node newChild : newChilds) {
                element.appendChild(newChild);
            }
        }
    }

    public static String xml2str(org.w3c.dom.Node n) {
        String str = null;
        StringWriter sw = null;
        try {
            sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(n);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(sw);
            transformer.transform(source, result);
            str = sw.toString();
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException ignored) {
                }
            }
        }
        return str;
    }

    public static String elementTextByPath(Element root, String path) {
        String delim = "/";
        if (path.startsWith(delim)){
            path = path.substring(1);
        }
        String[] split = path.split(delim);
        org.w3c.dom.Node node = root;
        boolean b = false;
        for (String str : split) {
            if (node.getNodeName().equals(str)) {
                continue;
            }
            NodeList childNodes = node.getChildNodes();
            for (int k = 0; k < childNodes.getLength(); k++) {
                org.w3c.dom.Node item = childNodes.item(k);
                if (item.getNodeType() == Node.ELEMENT_NODE && item.getNodeName().equals(str)) {
                    b = true;
                    node = item;
                    break;
                }
            }
        }
        if (b) {
            return node.getTextContent();
        }
        return null;
    }

    public static List<org.w3c.dom.Node> elementListByPath(Element root, String path) {
        List<org.w3c.dom.Node> temp = new ArrayList<>();
        org.w3c.dom.Node currentNode = root;
        String delim = "/";
        if (path.startsWith(delim)){
            path = path.substring(1);
        }
        String[] split = path.split(delim);
        String end = split[split.length - 1];

        for (String str : split) {
            if (currentNode.getNodeName().equals(str)) {
                continue;
            }
            NodeList childNodes = currentNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                org.w3c.dom.Node item = childNodes.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    if (item.getNodeName().equals(end)) {
                        temp.add(item);
                    } else if (item.getNodeName().equals(str)) {
                        currentNode = item;
                        break;
                    }
                }
            }
        }
        return temp;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        /*DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element SVCCode = document.createElement("SVCCode");
        SVCCode.setTextContent("ECS029002");
        Element Ver = document.createElement("Ver");
        Ver.setTextContent("20140514001");
        Element ActionCode = document.createElement("ActionCode");
        ActionCode.setTextContent("0");
        Element TxID = document.createElement("TxID");
        TxID.setTextContent("按要求生成");
        Element PlatNo = document.createElement("PlatNo");
        PlatNo.setTextContent("93510");
        Element TimeStamp = document.createElement("TimeStamp");
        TimeStamp.setTextContent("yyyy-MM-dd HH:mm:ss");

        Element Header = document.createElement("Header");
        Header.appendChild(SVCCode);
        Header.appendChild(Ver);
        Header.appendChild(ActionCode);
        Header.appendChild(TxID);
        Header.appendChild(PlatNo);
        Header.appendChild(TimeStamp);


        Element Ticket = document.createElement("Ticket");
        Ticket.setTextContent("TicketXXXX");
        Element AuthCode = document.createElement("AuthCode");
        AuthCode.setTextContent("AuthCodeXXXX");

        Element Body = document.createElement("Body");
        Body.appendChild(Ticket);
        Body.appendChild(AuthCode);

        Element ECSRoot = document.createElement("ECSRoot");
        ECSRoot.appendChild(Header);
        ECSRoot.appendChild(Body);

        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(ECSRoot);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(System.out);
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            transformer.transform(source, result);
            System.out.println(sw.toString());
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }*/
        String xml = "<ECSRoot>\n" +
                "  <Header>\n" +
                "    <SVCCode>ECS029002</SVCCode>\n" +
                "    <Ver>20140514001</Ver>\n" +
                "    <ActionCode>1</ActionCode>\n" +
                "    <TxID>900008c692ac7cb9542a299f1812145a6b6a4</TxID>\n" +
                "    <PlatNo>90000</PlatNo>\n" +
                "    <TimeStamp>2015-12-17 11:28:47</TimeStamp>\n" +
                "    <RspType>0</RspType>\n" +
                "    <RspCode>0000</RspCode>\n" +
                "    <Desc>Success</Desc>\n" +
                "  </Header>\n" +
                "  <Body>\n" +
                "    <PID>02</PID>\n" +
                "    <AreaCode />\n" +
                "    <CityNo />\n" +
                "    <UID>20150000000000013173</UID>\n" +
                "    <UName>15317713190</UName>\n" +
                "    <UType>201</UType>\n" +
                "    <PwdType>04</PwdType>\n" +
                "    <NickName>15317713190</NickName>\n" +
                "    <SSOPlatNo>93510</SSOPlatNo>\n" +
                "    <SSOURL>http://test.189.cn:8080/services/login/sso</SSOURL>\n" +
                "    <IPAddr>172.16.50.64</IPAddr>\n" +
                "    <Ticket>90000272976024b282d6c29b41cbb7f41a600ef30955</Ticket>\n" +
                "    <UStatus>00</UStatus>\n" +
                "  </Body>\n" +
                "</ECSRoot>";
        Element element = xmlToElement(xml);
        String userName = elementTextByPath(element, "ECSRoot/Body/UName");
        System.out.println(userName);
        String userType = elementTextByPath(element, "ECSRoot/Body/UType");
        System.out.println(userType);
//        document.

    }

}
