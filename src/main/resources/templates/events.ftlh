<!doctype html>
<html lang="en">
<head>
    <title>Events</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script>document.getElementsByTagName("html")[0].className += " js";</script>
    <link rel="stylesheet" href="../static/css/event-style.css">

    <link rel="shortcut icon" type="image/png" href="https://cdn.onlinewebfonts.com/svg/img_51582.png">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/css/buttons.css">
    <link rel="stylesheet" href="/static/css/text.css">
    <link rel="stylesheet" href="/static/css/menu.css">

    <script type="text/javascript" src="/static/javascript/js.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

    <div id="home" class="w3-top">
        <div class="w3-bar" id="myNavBar">
            <a href="/" class="w3-bar-item w3-button"><i class="fa fa-home"></i> HOME</a>
            <a href="#add-event" class="w3-bar-item w3-button"><i class="fa fa-plus"></i> ADD EVENT</a>
            <a href="#events" class="w3-bar-item w3-button"><i class="fa fa-calendar"></i> EVENTS</a>
            <a href="#footer" class="w3-bar-item w3-button"><i class="fa fa-chevron-down"></i> DOWN</a>
            <a href="#" class="w3-bar-item w3-button"><i class="fa fa-chevron-up"></i> UP</a>
            <a href="/login" class="w3-bar-item w3-button w3-hide-small w3-right w3-hover-red"><i class="fa fa-sign-in"></i></a>
        </div>

        <div id="navDemo" class="w3-bar-block w3-white w3-hide w3-hide-large w3-hide-medium">
            <a href="/" class="w3-bar-item w3-button w3-right w3-hover-red"><i class="fa fa-home"></i></a>
            <a href="#" class="w3-bar-item w3-button w3-right w3-hover-red"><i class="fa fa-chevron-up"></i>UP</a>
            <a href="/login" class="w3-bar-item w3-button w3-right w3-hover-red"><i class="fa fa-sign-in"></i></a>
        </div>
    </div>

    <div class="parallax big-img-greeting-1 w3-display-container w3-opacity-min" id="home">
        <div class="w3-display-middle" style="white-space:nowrap;">
            <span class="w3-center w3-padding-large w3-black w3-xlarge w3-wide w3-animate-opacity">Events</span>
        </div>
    </div>

    <div class="w3-content w3-container w3-padding-32" id="add-event">
        <div class="w3-center w3-row">
            <h3 class="w3-center">Add event</h3>
            <form action="/events/add" method="post">
                <input id="title" name="title" type="text" placeholder="t e x t" style="text-align:center; width: 250px;"/>
                <br><br>
                <input id="from" name="from" type="text" placeholder="f r o m ( H H : M M )" style="text-align:center; width: 250px;"/>
                <br><br>
                <input id="to" name="to" type="text" placeholder="t o ( H H : M M )" style="text-align:center; width: 250px;"/>
                <br><br>
                <input id="date" name="date" type="date" placeholder="d a t e" style="text-align:center; width: 250px;"/>
                <br><br>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn1 w3-button w3-padding-large" style="box-shadow:none;">
                    Add
                </button>
            </form>
        </div>
    </div>

    <div id="events">
        <div class="cd-schedule cd-schedule--loading margin-top-lg margin-bottom-lg js-cd-schedule">
            <div class="cd-schedule__timeline">
                <ul>
                    <li><span>09:00</span></li>
                    <li><span>09:30</span></li>
                    <li><span>10:00</span></li>
                    <li><span>10:30</span></li>
                    <li><span>11:00</span></li>
                    <li><span>11:30</span></li>
                    <li><span>12:00</span></li>
                    <li><span>12:30</span></li>
                    <li><span>13:00</span></li>
                    <li><span>13:30</span></li>
                    <li><span>14:00</span></li>
                    <li><span>14:30</span></li>
                    <li><span>15:00</span></li>
                    <li><span>15:30</span></li>
                    <li><span>16:00</span></li>
                    <li><span>16:30</span></li>
                    <li><span>17:00</span></li>
                    <li><span>17:30</span></li>
                    <li><span>18:00</span></li>
                </ul>
            </div>

            <#--EVENTS HERE-->

            <div class="cd-schedule-modal">
                <header class="cd-schedule-modal__header">
                    <div class="cd-schedule-modal__content">
                        <span class="cd-schedule-modal__date"></span>
                        <h3 class="cd-schedule-modal__name"></h3>
                    </div>
                    <div class="cd-schedule-modal__header-bg"></div>
                </header>

                <div class="cd-schedule-modal__body">
                    <div class="cd-schedule-modal__event-info"></div>
                    <div class="cd-schedule-modal__body-bg"></div>
                </div>
                <a href="#0" class="cd-schedule-modal__close text-replace">Close</a>
            </div>

            <div class="cd-schedule__cover-layer"></div>
        </div>
    </div>

    <script src="../static/javascript/util.js"></script>
    <script src="../static/javascript/main.js"></script>

    <div class="parallax big-img-greeting-small w3-display-container w3-opacity-min"></div>

    <footer id="footer" class="w3-center w3-black w3-padding-32 w3-opacity w3-hover-opacity-off">
        <a href="#home" class="" style="text-decoration: none; color: #ABADAA;"><i class="fa fa-arrow-up w3-margin-right"></i>To the top</a>
        <div class="w3-xlarge w3-section">
            <a href="" target="_blank"
               class="fa fa-git w3-hover-opacity" style="text-decoration: none;"></a>
        </div>
    </footer>
</body>
</html>