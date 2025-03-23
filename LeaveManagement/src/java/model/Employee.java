/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Employee {
    private int eid;
    private String ename;
    private String dept;
    private String role;
    private Integer manager_id; // Nullable nếu là Boss

    public Employee() {
    }

    public Employee(int eid, String ename, String dept, String role, Integer manager_id) {
        this.eid = eid;
        this.ename = ename;
        this.dept = dept;
        this.role = role;
        this.manager_id = manager_id;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getManager_id() {
        return manager_id;
    }

    public void setManager_id(Integer manager_id) {
        this.manager_id = manager_id;
    }

    
    
    
    
}
