# JTableSpringHibernateDemo
Detailed description is provided in the following articles:
- [JTable Spring Hibernate Demo] (http://www.codeproject.com/Articles/1113148/JTable-Spring-Hibernate-Demo)
- [Changing JTable layout] (http://www.codeproject.com/Articles/1113206/Changing-JTable-Layout)
- [JTable as HTML select element] (http://www.codeproject.com/Articles/1113216/JTable-as-HTML-select-element)

######Running the Demo: 
Once the project is deployed, Home Page will be displayed.
The DataBase Setup button in the main menu on the Home Page calls the DatabaseSetupController. DataBaseSetupController was defined to create the tables and insert records. Every time the button is clicked, database will be populated with the seed records to get the user started. This is essential for correct functioning of the demo.
I used IntelliJ IDEA 2016.1.1 for the demo. For deploying the code correctly, make sure you do the following steps correctly:
- Select the Project Structure icon   from the toolbar.
- Go to Artifacts tab.
- Change the Output directory path to appropriate path.

######Library:
- Option 1: 
pom.xml is provided for the project. Open the project in IntelliJ IDEA, right click on pom.xml. Click on Maven. Select Generate Sources and Update Folders options. IntelliJ IDEA automatically downloads the necessary dependencies as mentioned in pom.xml. 
- Option 2: 
All the required libraries are available at https://github.com/pujagani/JTableSpringHibernateDemoLibs.
Download them and copy to the lib folder.
