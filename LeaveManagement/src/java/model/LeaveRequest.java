/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class LeaveRequest {
    private int rid;
    private String title;
    private String reason;
    private Date from;
    private Date to;
    private String created_by;
    private int status; // 0 - Pending, 1 - Approved, 2 - Rejected
    private Date created_date;
    private String reject_reason; // Lý do reject (nếu có)

    public LeaveRequest() {
    }

    public LeaveRequest(int rid, String title, String reason, Date from, Date to, String created_by, int status, Date created_date, String reject_reason) {
        this.rid = rid;
        this.title = title;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.created_by = created_by;
        this.status = status;
        this.created_date = created_date;
        this.reject_reason = reject_reason;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }
}

