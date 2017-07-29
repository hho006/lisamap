<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<title>My Map</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<head>
    <style>
        header {
            width: 100%;
            height: 60px;
            color: white;
            background-color: black;
            text-align: center;
            font: 40px bold;
            font-family: Arial, Helvetica, sans-serif;
            top: 0;
            position: fixed;
        }
        .header-div {
            height: 60px;
            line-height: 60px;
            vertical-align: middle;
        }
        footer {
            width: 100%;
            height: 30px;
            color: white;
            background-color: black;
            text-align: center;
            bottom: 0;
            position: fixed;
        }
        .footer-div {
            height: 30px;
            line-height: 30px;
            vertical-align: middle;
        }
        .nav-custom {
            width: 100%;
            border-radius: 0;
            top: 60px;
            position: fixed;
        }
        .navbar-nav > li > a {
            width: 115px;
            font-size: 15px;
            text-align: center;
        }
        .navbar-header > a {
            width: 115px;
            font-size: 15px;
            text-align: center;
        }
        .dropdown {
            position: relative;
            display: inline-block;
        }
        .dropdown-menu {
            display: none;
            position: absolute;
            background-color: #a0a0a0;
            min-width: 115px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }
        .dropdown:hover .dropdown-menu {
            display: block;
        }
        .content-block {
            width: 100%;
            height: 100%;
            padding-top: 120px;
            padding-bottom: 40px;
            padding-left: 30px;
            padding-right: 30px;
            color: black;
            background-color: white;
        }
        .ocean {
            fill: #00248F;
        }
        .land {
            fill: #A98B6F;
            stroke: #FFF;
            stroke-width: 0.5px;
        }
        .land:hover {
            fill:#CCCC33;
            stroke-width: 2px;
        }
        .focused {
            fill: #33CC33;
        }
        .tooltip {
            position: absolute;
            display: none;
            pointer-events: none;
            background: #fff;
            padding: 5px;
            text-align: left;
            border: solid #ccc 1px;
            color: #666;
            font-size: 14px;
            font-family: sans-serif;
        }
        .selectList {
            position: absolute;
            border: solid #ccc 1px;
            padding: 3px;
            box-shadow: inset 1px 1px 2px #ddd8dc;
        }
    </style>
</head>

<body>
    <header><div class="header-div">LISA MAP</div></header>
    
    <nav class="navbar navbar-inverse nav-custom">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%= response.encodeURL(request.getContextPath() + "/") %>"><span class="glyphicon glyphicon-home"></span> Home</a>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/map") %>"><span class="glyphicon glyphicon-globe"></span> My Map</a></li>
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/photo/album") %>"><span class="glyphicon glyphicon-picture"></span> My Photo</a></li>
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/video/album") %>"><span class="glyphicon glyphicon-facetime-video"></span> My Video</a></li>
                <li class="dropdown">
                    <a href="#"><span class="glyphicon glyphicon-comment"></span> My Blog</a>
                    <ul class="dropdown-menu">
                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/blog") %>">View My Blog</a></li>
                        <li><a href="<%= response.encodeURL(request.getContextPath() + "/blog/search") %>">Search Blog</a></li>
                    </ul>
                </li>
                <li><a href="<%= response.encodeURL(request.getContextPath() + "/about") %>"><span class="glyphicon glyphicon-star-empty"></span> About Us</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% if(session.getAttribute("username") == null) { %>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/signup") %>"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/login") %>"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                <% } else { %>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/profile") %>"><span class="glyphicon glyphicon-user"></span> My Profile</a></li>
                    <li><a href="<%= response.encodeURL(request.getContextPath() + "/logout") %>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
    
    <div id="content" class="content-block">
        <script src="http://d3js.org/d3.v4.js"></script>
        <script src="http://d3js.org/topojson.v1.min.js"></script>
        <script src="http://d3js.org/queue.v1.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        
        <script>
            var width = 500, height = 500, scale = 0.25, focused = false;

            //Setting projection
            var projection = d3.geoOrthographic()
                .scale(200)
                .rotate([0, 0])
                .translate([width*0.5, height*0.5])
                .clipAngle(90);

            var path = d3.geoPath()
                .projection(projection);

            //SVG container
            var svg = d3.select("#content").append("svg")
                .attr("width", width)
                .attr("height", height)
                .style("background-color", "red");

            //Adding ocean
            svg.append("path")
                .datum({type: "Sphere"})
                .attr("class", "ocean")
                .attr("d", path);

            //Hover country name tooltip
            var countryTooltip = d3.select("#content").append("div").attr("class", "tooltip");

            //Country list for select
            var countrySelectList = d3.select("#content").append("select").attr("class", "selectList").attr("name", "countries");

            queue()
                .defer(d3.json, "<%= response.encodeURL(request.getContextPath() + "/resources/geodata/world-110m.json") %>") //worldMap
                .defer(d3.tsv, "<%= response.encodeURL(request.getContextPath() + "/resources/geodata/world-110m-country-names.tsv") %>") //countryData
                .await(ready);

            function ready(error, worldMap, countryData) {
                var countries = topojson.feature(worldMap, worldMap.objects.countries).features;
                var countryIds = {};
                countryData.forEach(function(d) {
                    countryIds[d.id] = d.name;
                    countrySelectList.append("option")
                        .text(d.name)
                        .property("value", d.id);
                });
                var selectedCountryId = -999;

                //Adding country land
                svg.selectAll("path.land")
                    .data(countries)
                    .enter().append("path")
                    .attr("class", "land")
                    .attr("d", path)
                //Mouse hover event ...
                    .on("mouseover", function(d) {
                        countryTooltip.text(countryIds[d.id])
                        .style("left", (d3.event.pageX + 7) + "px")
                        .style("top", (d3.event.pageY - 15) + "px")
                        .style("display", "block")
                        .style("opacity", 1);
                    })
                    .on("mouseout", function(d) {
                        countryTooltip.style("opacity", 0)
                        .style("display", "none");
                    })
                    .on("mousemove", function(d) {
                        countryTooltip.style("left", (d3.event.pageX + 7) + "px")
                        .style("top", (d3.event.pageY - 15) + "px");
                    })
                    .on("click", function(d) {
                        // TODO: focus then rotate country to map center
                        //send json request by ajax
                        var userid = <%= session.getAttribute("userid") %>;
                        var req = {"userId":userid, "countryId":d.id, "countryName":countryIds[d.id]}
                        $.ajax({
                            type: "POST",
                            contentType : "application/json; charset=utf-8",
                            url: "<%= response.encodeURL(request.getContextPath() + "/ajax/map/album") %>",
                            data: JSON.stringify(req),
                            dataType : 'json',
                            success: function (result) {
                                // show matched albums
                                if(result.statusCode == 200 && selectedCountryId != result.countryId) {
                                	selectedCountryId = result.countryId;
                                	$("#album_content").empty();
                                    $("#album_content").append("<h4>" + countryIds[selectedCountryId] + "</h4>");
                                    var albums = result.albumList;
                                    var covers = result.coverImageUrlList;
                                    for(i=0; i<albums.length; i++) {
                                        var element = "";
                                        var imgUrl = "<%= request.getContextPath() %>" + "/photo/album/" + albums[i].albumId;
                                        element += ("<a href=\"" + imgUrl + "\">");
                                        element += ("<img alt=\"Image not displayed\" src=\"" + covers[i] + "\" border=\"2\" height=\"200\" width=\"200\"/>");
                                        element += "</a><br>";
                                        $("#album_content").append(element);
                                    }
                                }
                            },
                            error: function (result) {
                                // TODO: show error message
                            }
                        });
                    });
                
                //Mouse drag event
                d3.selectAll("path.land, path.ocean")
                    .call(d3.drag()
                        .subject(function() { var r = projection.rotate(); return {x: r[0]/scale, y: -r[1]/scale}; })
                        .on("drag", function() {
                            var rotate = projection.rotate();
                            projection.rotate([d3.event.x*scale, -d3.event.y*scale, rotate[2]]);
                            svg.selectAll("path.land").attr("d", path);
                            svg.selectAll(".focused").classed("focused", focused = false);
                        })
                    );
                
                //Country focus on select list ...
                d3.select("select").on("change", function() {
                    var rotate = projection.rotate(),
                    focusedCountry = country(countries, this),
                    p = d3.geoCentroid(focusedCountry);
                    svg.selectAll(".focused").classed("focused", focused = false);

                    //Globe rotating ...
                    (function transition() {
                        d3.transition()
                        .duration(2500)
                        .tween("rotate", function() {
                            var r = d3.interpolate(projection.rotate(), [-p[0], -p[1]]);
                            return function(t) {
                                projection.rotate(r(t));
                                svg.selectAll("path").attr("d", path)
                                .classed("focused", function(d, i) { return d.id == focusedCountry.id ? focused = d : false; });
                            };
                        })
                    })();
                });

                function country(cnt, sel) { 
                    for(var i = 0, l = cnt.length; i < l; i++) {
                        if(cnt[i].id == sel.value) {return cnt[i];}
                    }
                };
            }
        </script>
        
        <h4>View My Albums</h4>
        <div id="album_content"></div>
        
    </div>
    
    <footer><div class="footer-div">Powered by HOHOHO</div></footer>
</body>
</html>