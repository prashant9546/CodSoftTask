<%-- 
    Document   : index
    Created on : Mar 3, 2025, 11:14:07 PM
    Author     : NISHANT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>STUDENT GRADE CALCULATOR</title>
    <link href="https://fonts.googleapis.com/css?family=Righteous&display=swap" rel="stylesheet">
    <style>
        body { background:#185adb; font-size: 12px; font-family: 'Righteous', cursive; }
        .container { width: 50%; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); }
        .app-form-control { width: 100%; padding: 10px; margin: 10px 0; font-size: 14px; border: 1px solid #ccc; }
        .app-form-button { background: #185adb; color: white; padding: 10px; border: none; cursor: pointer; }
        .app-form-button:hover { background: darkblue; }
        .showdata { font-size: 1.2rem; text-align: center; }
        #result { font-size: 1.4rem; text-align: center; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h2 style="text-align: center; color: #185adb;">Student Grade Calculator</h2>
        
        <form method="post">
            <input type="text" name="wd" class="app-form-control" placeholder="JAVA MARKS" required>
            <input type="text" name="maths" class="app-form-control" placeholder="PYTHON MARKS" required>
            <input type="text" name="comp" class="app-form-control" placeholder="SQL MARKS" required>
            <input type="text" name="phy" class="app-form-control" placeholder="WEB TECH MARKS" required>
            <input type="text" name="reactMarks" class="app-form-control" placeholder="REACT MARKS" required>
            <input type="submit" value="Calculate" class="app-form-button">
        </form>

        <div class="showdata">
            <% 
                // Fetch form data
                String wd = request.getParameter("wd");
                String maths = request.getParameter("maths");
                String comp = request.getParameter("comp");
                String phy = request.getParameter("phy");
                String reactMarks = request.getParameter("reactMarks");

                if (wd != null && maths != null && comp != null && phy != null && reactMarks != null) {
                    try {
                        // Convert input values to numbers
                        double wdMarks = Double.parseDouble(wd);
                        double mathsMarks = Double.parseDouble(maths);
                        double compMarks = Double.parseDouble(comp);
                        double phyMarks = Double.parseDouble(phy);
                        double reactMarksValue = Double.parseDouble(reactMarks);

                        // Calculate total and percentage
                        double totalGrades = wdMarks + mathsMarks + compMarks + phyMarks + reactMarksValue;
                        double perc = (totalGrades / 500) * 100;
                        String grades = "";

                        // Determine grade
                        if (perc >= 80) {
                            grades = "A";
                        } else if (perc >= 60) {
                            grades = "B";
                        } else if (perc >= 40) {
                            grades = "C";
                        } else {
                            grades = "F";
                        }

                        // Display results
            %>
                        <p>Out of 500, your total is <b><%= totalGrades %></b> and percentage is <b><%= String.format("%.2f", perc) %>%</b>.</p>
                        <p>Your grade is <b><%= grades %></b>.</p>
                        <p id="result" style="color: <%= perc >= 39.5 ? "green" : "red" %>;">
                            <%= perc >= 39.5 ? "PASS" : "FAIL" %>
                        </p>
            <%
                    } catch (NumberFormatException e) {
                        out.println("<p style='color:red;'>Invalid input! Please enter numeric values.</p>");
                    }
                }
            %>
        </div>
    </div>

    </body>
</html>
