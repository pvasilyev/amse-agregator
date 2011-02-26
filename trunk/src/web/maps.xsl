<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    
    <xsl:template match="record" mode="googlemap">
		 <!--placeMarker(new google.maps.LatLng( <xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>,<xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>),<xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>);-->
		placeMarker(new google.maps.LatLng( <xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>,<xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>),"<xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>");
	</xsl:template>

	
	<xsl:template name="scriptBlock">
	  <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 
				<script type="text/javascript"> 
				var stockholm = new google.maps.LatLng(59.32522, 18.07002);
				var parliament = new google.maps.LatLng(59.327383, 18.06747);
				var map;
 
	  			function initialize() {
	   				var mapOptions = {
	      				zoom: 1,
	      				mapTypeId: google.maps.MapTypeId.ROADMAP,
	      				center: stockholm
	    			};
	 
	    			map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
          
  					google.maps.event.addListener(map, 'click', function(event) {
   						<!-- placeMarker(event.latLng,"");-->
  					});
  					
  					<xsl:apply-templates select="//data[@id='maps']//record" mode="googlemap"/>
  				}
  				
  				function placeMarker(location, about) {
  					var marker = new google.maps.Marker({
      					position: location, 
      					map: map,
      					title: about
  					});
  					
    			}
			</script>
		</xsl:template>
    
</xsl:stylesheet>