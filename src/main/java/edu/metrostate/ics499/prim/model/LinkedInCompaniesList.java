package edu.metrostate.ics499.prim.model;

import java.io.Serializable;

public class LinkedInCompaniesList implements Serializable {
    private int _count;
    private int _start;
    private int _total;
    private LinkedInCompany values[];

    /**
     * Gets _count
     *
     * @return value of _count
     */
    public int get_count() {
        return _count;
    }

    /**
     * Sets _count to the specified value in _count
     *
     * @param _count the new value for _count
     */
    public void set_count(int _count) {
        this._count = _count;
    }

    /**
     * Gets _start
     *
     * @return value of _start
     */
    public int get_start() {
        return _start;
    }

    /**
     * Sets _start to the specified value in _start
     *
     * @param _start the new value for _start
     */
    public void set_start(int _start) {
        this._start = _start;
    }

    /**
     * Gets _total
     *
     * @return value of _total
     */
    public int get_total() {
        return _total;
    }

    /**
     * Sets _total to the specified value in _total
     *
     * @param _total the new value for _total
     */
    public void set_total(int _total) {
        this._total = _total;
    }

    /**
     * Gets values
     *
     * @return value of values
     */
    public LinkedInCompany[] getValues() {
        return values;
    }

    /**
     * Sets values to the specified value in values
     *
     * @param values the new value for values
     */
    public void setValues(LinkedInCompany[] values) {
        this.values = values;
    }
}
