<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE HTML>
<html xmlns:xlink="http://www.w3.org/1999/xlink">
<head>
<title>BloCol</title>
<!-- Custom Theme files -->
<link href="resources/css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<!-- Custom Theme files -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--Google Fonts-->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<!--Google Fonts-->
<style type="text/css">
.node circle {
	fill: #fff;
	stroke: steelblue;
	stroke-width: 1.5px;
}

.node {
	font: 10px sans-serif;
}

.link {
	fill: none;
	stroke: #ccc;
	stroke-width: 1.5px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
</head>
<body>
	<!--search start here-->
	<div id="s-bar" class="search">
		<i> </i>
		<div class="s-bar">
			<input id="q_url" type="text" value="Blog Url"
				onfocus="this.value = '';"
				onblur="if (this.value == '') {this.value = 'Blog Url';}"> <input
				id="blocol" type="submit" value="BloCol" />
		</div>
		<!-- <p>Popular searches: <a href="#">Modern PSD template,</a> <a href="#"> Portfolio design </a></p> -->
	</div>
	<!--search end here-->
	<div id="cartesian"></div>
	<div id="radial"></div>

</body>
<script type="application/javascript">
	
	
	
	
	
$(document).ready(function() {
		$("input#blocol").click(function() {
			$("div#s-bar").hide();
			$.get("http://localhost:8080/blocol/api/url", {
				"q_url" : $("#q_url").val()
			}, function(data, status) {
				if (status == "success") {
					cartesian(data);
					radial(data);
				}
			});
		});
	});


	function cartesian(data){
		var width = 960,
	    height = 2200;

		var cluster = d3.layout.cluster()
		    .size([height, width - 160]);
	
		var diagonal = d3.svg.diagonal()
		    .projection(function (d) {
		    return [d.y, d.x];
		});
	
		var svg = d3.select("body").append("svg")
		    .attr("width", width)
		    .attr("height", height)
		    .append("g")
		    .attr("transform", "translate(40,0)");

	    var nodes = cluster.nodes(data),
	        links = cluster.links(nodes);

	    var link = svg.selectAll(".link")
	        .data(links)
	        .enter().append("path")
	        .attr("class", "link")
	        .attr("d", diagonal);

	    var node = svg.selectAll(".node")
	        .data(nodes)
	        .enter().append("g")
	        .attr("class", "node")
	        .attr("transform", function (d) {
	        return "translate(" + d.y + "," + d.x + ")";
	    })

	    node.append("circle")
	        .attr("r", 4.5);

	    node.append("svg:a")
		  .attr("xlink:href", function(d){return d.url;}).attr("xlink:data-toggle","tooltip")
		  .attr("xlink:data-placement","bottom").attr("xlink:title",function(d){return d.url;})
		  .append("text")
	        .attr("dx", function (d) { return d.children ? -8 : 8; })
	        .attr("dy", 3)
	        .style("text-anchor", function (d) { return d.children ? "end" : "start"; })
	        .style("fill","white").style("font-size","13px")
	        .text(function (d) { return d.name; });

	d3.select(self.frameElement).style("height", height + "px");}

	function radial(data) {
		var radius = 960 / 2;

		var cluster = d3.layout.cluster().size([ 360, radius - 120 ]);

		var diagonal = d3.svg.diagonal.radial().projection(function(d) {
			return [ d.y, d.x / 180 * Math.PI ];
		});

		var svg = d3.select("div#radial").append("svg").attr("width",
				radius * 2).attr("height", radius * 2).append("g").attr(
				"transform", "translate(" + radius + "," + radius + ")");

		var nodes = cluster.nodes(data);

		var link = svg.selectAll("path.link").data(cluster.links(nodes))
				.enter().append("path").attr("class", "link").attr("d",
						diagonal);

		var node = svg.selectAll("g.node").data(nodes).enter().append("g")
				.attr("class", "node").attr("transform", function(d) {
					return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")";
				})

		node.append("circle").attr("r", 4.5);

		node.append("svg:a")
		  .attr("xlink:href", function(d){return d.url;}).attr("xlink:data-toggle","tooltip")
		  .attr("xlink:data-placement","bottom").attr("xlink:title",function(d){return d.url;})
		  .append("text").attr("dy", ".31em").attr("text-anchor",function(d) {
				return d.x < 180 ? "start" : "end";
			}).attr("transform", function(d) {
		return d.x < 180 ? "translate(8)" : "rotate(180)translate(-8)";
	}).style("fill","white").style("font-size","13px").text(function(d) {
		return d.name;
	});  // <-- reading the new "url" property
		

		d3.select(self.frameElement).style("height", radius * 2 + "px");
	}












</script>
</html>