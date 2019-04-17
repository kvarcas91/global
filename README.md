# Global Music 

# Development Guide

### Creating classes

Do not add controller in .fxml file as it is done manually in Loader.class.

Because Controller cannot be linked with fxml file inside .fxml file, all ActionEvents
must be handled manually:
    
    @FXML JFXButton mButton;
    @FXML ImageView mImageVIew;
    
    mImageView.setOnMousePressed(e -> {
        // your on click event code
        // if it is larger that 2 lines, consider to use another method and link it here
    });
    
    mButton.setOnAction(e -> {
        // your code
    });

Static factory > public constructors and all constructors must be private in 
order to prevent for memory leaks and additional unnecessary object creation:

    class mClass {
        private static mClass instance = null;
        
        private mClass () {
            instance = this;
        }
        
        public static mClass getInstance () {
            if (instance == null) {
                synchronized (mClass.class) {
                if (instance == null) {
                    return new mClass ();   
                    }
                }
            }
            return instance;
        }
    }
    
there instance will be a reference to your class. In addition, it makes sure that 
it is singleton. 

If your class will be part of navigation process (visible to end user) - it must extend _Controller_ class.

    public class mClass extends Controller {
        private static mClass instance = null;
        
        ... private constructor ...
        ... public static factory method ...
        ... your code ...
        
        @Override
        public void Destroy () {
            if (instance != null) {
                LOGGER.log(Level.INFO, "Destroying mClass instance at: {0}\n", LocalTime.now());
                instance = null;
            }
        }
    }

#
### Creating Objects

It must extend Entity class and inject your class:

    class mObject extends Entity<mObject> {}
    
Also, you will need to override few methods:

    class mObject extends Entity<mObject> {
        // getters and setters
        
        @Override
        public String getQuery() {
            // return String value of insert query 
        }
    
        @Override
        public void setObject(HashMap<String, String> object) {
            // set your object
            // object hashmap: k -> column name in DB; v -> value of that column
        }
    
        @Override
        public Bands getObject() {
            return this;
        }
    
        @Override
        public String toString () {
            // provide toString for your object for debuging purpose
        }
    }

#
### Loader

If you need to load another page, use Loader class:

    // @param path - your .fxml file path. i.e.: "../UI/account.fxml"
    // @param controller - your controller class which you want to load
    Loader.getInstance().loadPage(String path, Controller controller);
    
    //or
    
    Loader loader = Loader.getInstance();
    loader.loadPage(...);
    
    // i.e.
    
    Loader.getInstance().loadPage("../UI.account.fxml", AccountController.getInstance());

Keep in mind, that your controller must extend Controller class, otherwise it will throw an exception

#
### Database

**JDBC.class has these public methods:**

    JDBC getInstance () // returns JDBC instance
    
example: 

    JDBC database = JDBC.getInstance();


**to create connection (if its not done yet):**
    
    void createConnection() // connects to mySQL server
    
example:

    JDBC.createConnection();
    
**If you need to check if it is connected to the server:**

    boolean isConnected () // checks if driver is connected to database
    
If it is not connected to database, it will also shows the NotificationPane, so you
do not need to cast it.

example:

    if (JDBC.isConnected) {
    // your code
    }
    
**Insert object in database:** 

    boolean insert (String query)
    
example:
    
    // create an object
    User user = new User("name", "lastName");
    
    // get query from that object and inject in insert method
    JDBC.insert(user.getQuery());
    
**Get single object:**

    Entity get (String query, String className) // returns object

You will need to typecast the return value. Also, it might return null:
    
    String query = ...;
    User user = (Entity)JDBC.get(query, User.class.getName());

**Get all objects:**

    Entity getAll (String query, String className) // returns ArrayList<Entity>

You will need to typecast the return value. Also, it might return null:

    ArrayList<User> users = new ArrayList<>();
    String query = ...;
    User temp;
    
    ArrayList<Entity> objects = JDBC.getAll(query, User.class.getName());
    for (Entity obj : objects) {
        temp = (User) obj;
        users.add(temp);
    }

**Delete object from database:**

    boolean delete (String table, String columnID, int ID)

It takes table name, column name and object id as parameters:
    
    @param table - table name of your database
    @param columnID - column name in your table (preferable ID column)
    @param ID - your object ID

example:

    int id = user.getUserID();
    if (JDBC.delete("USERS", "User_ID", id)) {
        NotificationPane.show("User has been removed", "green");
    }
    
**Edit entity:**

    // work in progress

#
### Notifications

If you need to show a notification in main window:

    NotificationPane.show(String message);
    
or

    // colour here is Hex Colour Code or verbose (i.e. 'green')
    NotificationPane.show(String message, String colour); 


If you want to show notification in your own Layout, first you need to implement
_Notifications_ interface and override _initializeNotificationPane_ method with 
dependency injection:

    @Override
        public void initializeNotificationPane() {
            // @param Pane - your notification layout
            // @param Text - Text widget which will show message
            NotificationPane.createInstance(Pane pane, Text textField);
        }
        
#
### Logging

If you want to participate in Logging process - Create a Logger:
    
     private static final Logger LOGGER = Logger.getLogger(yourClass.class.getName());
     
Add Handler to your Logger (it will write logs in .txt file):

    WriteLog.addHandler(LOGGER);
    
And start to use it:

    // there can be different Levels {Warning, Severe, Info etc.}
    LOGGER.log(Level.INFO, "some log text: {0}\n", LocalTime.now());

#
### Validator

If you need to check if your TextField (including JFXTextField and
JFXPasswordField) is empty and/or is less than 4 characters, you can use 
Validator class. It returns boolean value based on if any of given TextFields are not empty and
has more than 4 characters.

    if (Validators.validate(varargs... TextFields)) {
        // your code
    }
    
    i.e.
    
    @FXML JFXTextField userNameField;
    @FXML JFXPasswordField passwordField;
    
    ...
    
    if (Validators.validate(userNameField, passwordField) {
        System.out.println("true");
    }
    else System.out.println("true");

Keep in mind, that Validators.class calls NotificationPane.show(...) if
condition does not match, therefore you do not need to call it again.
    

    
