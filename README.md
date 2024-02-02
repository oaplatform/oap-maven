# oap-maven

The parent Maven OAP project. All Maven OAP projects should use it in the following manner:
```
    <parent>
        <groupId>oap</groupId>
        <artifactId>oap.maven</artifactId>
        <version>${oap.project.version}</version>
    </parent>
```
Currently it handles `maven-compiler-plugin`, `maven-surefire-plugin`, `maven-source-plugin`, `flatten-maven-plugin`, 
`maven-checkstyle-plugin`, `maven-enforcer-plugin`.</br> 
_Note: the above plugins still can be overridden in child Maven project, if needed._