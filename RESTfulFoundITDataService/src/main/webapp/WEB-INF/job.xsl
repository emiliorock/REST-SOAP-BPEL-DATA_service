<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
     <xsl:for-each select="jobalerts/job">
      <xsl:sort select="title" />
    <xsl:if test="contains(title, 'developer')">
      <link><xsl:value-of select="link"/></link>
      <title><xsl:value-of select="title"/></title>
      <description><xsl:value-of select="description"/></description>
   </xsl:if>
       <xsl:if test="contains(description, 'developer')">
      <link><xsl:value-of select="link"/></link>
      <title><xsl:value-of select="title"/></title>
      <description><xsl:value-of select="description"/></description>
   </xsl:if>
   </xsl:for-each>
</xsl:template>
</xsl:stylesheet>