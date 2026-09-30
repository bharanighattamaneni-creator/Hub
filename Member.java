package com.library.model;
public class Member {
 private int id; private String name,email,phone,department;
 public Member(int id,String name,String email,String phone,String department){this.id=id;this.name=name;this.email=email;this.phone=phone;this.department=department;}
 public int getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPhone(){return phone;} public String getDepartment(){return department;}
}
