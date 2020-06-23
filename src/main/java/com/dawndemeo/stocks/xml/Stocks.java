
package com.dawndemeo.stocks.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StockQuote" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="symbol" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="price" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="quoteDate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "stockQuote"
})
@XmlRootElement(name = "stocks")
public class Stocks {

    @XmlElement(name = "StockQuote")
    protected List<Stocks.StockQuote> stockQuote;

    /**
     * Gets the value of the stockQuote property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stockQuote property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStockQuote().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Stocks.StockQuote }
     * 
     * 
     */
    public List<Stocks.StockQuote> getStockQuote() {
        if (stockQuote == null) {
            stockQuote = new ArrayList<Stocks.StockQuote>();
        }
        return this.stockQuote;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="symbol" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="price" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="quoteDate" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class StockQuote {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "symbol")
        protected String symbol;
        @XmlAttribute(name = "price")
        protected String price;
        @XmlAttribute(name = "quoteDate")
        protected String quoteDate;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the symbol property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSymbol() {
            return symbol;
        }

        /**
         * Sets the value of the symbol property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSymbol(String value) {
            this.symbol = value;
        }

        /**
         * Gets the value of the price property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrice() {
            return price;
        }

        /**
         * Sets the value of the price property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrice(String value) {
            this.price = value;
        }

        /**
         * Gets the value of the quoteDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getQuoteDate() {
            return quoteDate;
        }

        /**
         * Sets the value of the quoteDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setQuoteDate(String value) {
            this.quoteDate = value;
        }

        @Override
        public String toString() {
            return "StockQuote{" +
                    "symbol=" + symbol +
                    ", price=" + price +
                    ", quoteDate=" + quoteDate +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Stocks{" +
                "stockQuote=" + stockQuote +
                '}';
    }
}
