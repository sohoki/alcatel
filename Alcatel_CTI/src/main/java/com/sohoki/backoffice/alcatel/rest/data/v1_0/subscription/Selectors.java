package com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription;

import java.util.ArrayList;
import java.util.List;

public class Selectors {

	    protected List<String> ids;
	    protected List<String> names;
	    protected List<String> families;
	    protected List<String> origins;

	    /**
	     * Gets the value of the id property.
	     * <p/>
	     * <p/>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the id property.
	     * <p/>
	     * <p/>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getIds().add(newItem);
	     * </pre>
	     * <p/>
	     * <p/>
	     * <p/>
	     * Objects of the following type(s) are allowed in the list
	     * {@link String }
	     */
	    public List<String> getIds() {
	        if (ids == null) {
	            ids = new ArrayList<>();
	        }
	        return this.ids;
	    }

	    /**
	     * Gets the value of the name property.
	     * <p/>
	     * <p/>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the name property.
	     * <p/>
	     * <p/>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getNames().add(newItem);
	     * </pre>
	     * <p/>
	     * <p/>
	     * <p/>
	     * Objects of the following type(s) are allowed in the list
	     * {@link String }
	     */
	    public List<String> getNames() {
	        if (names == null) {
	            names = new ArrayList<>();
	        }
	        return this.names;
	    }

	    /**
	     * Gets the value of the families property.
	     * <p/>
	     * <p/>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the families property.
	     * <p/>
	     * <p/>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getFamilies().add(newItem);
	     * </pre>
	     * <p/>
	     * <p/>
	     * <p/>
	     * Objects of the following type(s) are allowed in the list
	     * {@link String }
	     */
	    public List<String> getFamilies() {
	        if (families == null) {
	            families = new ArrayList<>();
	        }
	        return this.families;
	    }

	    /**
	     * Gets the value of the origins property.
	     * <p/>
	     * <p/>
	     * This accessor method returns a reference to the live list,
	     * not a snapshot. Therefore any modification you make to the
	     * returned list will be present inside the JAXB object.
	     * This is why there is not a <CODE>set</CODE> method for the origins property.
	     * <p/>
	     * <p/>
	     * For example, to add a new item, do as follows:
	     * <pre>
	     *    getOrigins().add(newItem);
	     * </pre>
	     * <p/>
	     * <p/>
	     * <p/>
	     * Objects of the following type(s) are allowed in the list
	     * {@link String }
	     */
	    public List<String> getOrigins() {
	        if (origins == null) {
	            origins = new ArrayList<>();
	        }
	        return this.origins;
	    }

	    @Override
	    public String toString() {
	        return "ids: " + getIds()
	                + ", names: " + getNames()
	                + ", families: " + getFamilies()
	                + ", origins: " + getOrigins();
	    }
}
