<%-- 
    Document   : index
    Created on : Mar 3, 2025, 11:38:56 PM
    Author     : NISHANT
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <title>Number Guessing Game</title>
    <style>
        html { font-family: sans-serif; }
        body { width: 50%; max-width: 800px; min-width: 480px; margin: 0 auto; }
        .form input[type="number"] { width: 200px; }
        .lastResult { color: white; padding: 3px; }
    </style>
</head>
<body>
    <h1>Number Guessing Game</h1>
    <p>We have selected a random number between 1 and 100. See if you can guess it in 10 turns or fewer. We'll tell you if your guess was too high or too low.</p>

    <%! int randomNumber = (int) (Math.random() * 100) + 1;
        int guessCount = 1; %>

    <form method="post">
        <label for="guessField">Enter a guess: </label>
        <input type="number" name="guessField" min="1" max="100" required>
        <input type="submit" value="Submit guess">
    </form>

    <% if (request.getMethod().equals("POST")) {
        int userGuess = Integer.parseInt(request.getParameter("guessField"));
        String message = "";
        String color = "red";
        if (guessCount == 1) {
            session.setAttribute("previousGuesses", "Previous guesses: ");
        }
        
        session.setAttribute("previousGuesses", session.getAttribute("previousGuesses") + " " + userGuess);
        
        if (userGuess == randomNumber) {
            message = "Congratulations! You got it right!";
            color = "green";
            session.setAttribute("gameOver", true);
        } else if (guessCount == 10) {
            message = "!!!GAME OVER!!!";
            session.setAttribute("gameOver", true);
        } else {
            message = "Wrong!";
            if (userGuess < randomNumber) {
                message += " Last guess was too low!";
            } else {
                message += " Last guess was too high!";
            }
        }
        guessCount++;
        session.setAttribute("guessCount", guessCount);
    %>

    <p class="guesses"><%= session.getAttribute("previousGuesses") %></p>
    <p class="lastResult" style="background-color: <%= color %>;"><%= message %></p>

    <% if (session.getAttribute("gameOver") != null) { %>
        <form method="post">
            <input type="submit" name="reset" value="Start new game">
        </form>
    <% } %>

    <% } 
        if (request.getParameter("reset") != null) {
            session.invalidate();
            response.sendRedirect("game.jsp");
        }
    %>
</body>
</html>