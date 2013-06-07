/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.entity;

import java.util.List;

/**
 *
 * @author Loiane Groner
 * http://loiane.com
 */
public class ExtGridSerializer {

    public ExtGridSerializer(List<Contact> data, int t) {
        this.data = data;
        this.total = t;
    }
    
    public List<Contact> data;

    public boolean success = true;

    public int total = 0;
}
