package com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription;

import java.util.List;
import java.util.ArrayList;

public class Filter {

	protected List<Selectors> selectors;

    /**
     * Gets the value of the selectors property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selectors property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelectors().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Selectors }
     */
    public List<Selectors> getSelectors() {
        if (selectors == null) {
            selectors = new ArrayList<>();
        }
        return this.selectors;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("selectors: [ ");
        for (Selectors selector : selectors) {
            result.append(selector);
            result.append(" ");
        }
        result.append("]");
        return result.toString();
    }
}
