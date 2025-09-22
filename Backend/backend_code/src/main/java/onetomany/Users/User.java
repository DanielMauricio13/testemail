
package onetomany.Users;

import java.util.*;

import jakarta.persistence.*;
import onetomany.Items.Item;




@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String emailId;
    private Date joiningDate;
    private boolean ifActive;
    private String UserPassword;
    private Date lastLoggin;


    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "user")
    List<Item> userItems;
    
    private int viewCount= 1;
    private int acceptanceCount=1;

    @Lob
    private byte[] profileImage;
    @ElementCollection
    private List<Integer> UserHobbiesLists;




 




    // =============================== Constructors ================================== //


    public User(String name, String emailId, String userPassword,String userName, Date birthday, int age, String gender  ) {
        this.name = name;
        this.emailId = emailId;
        this.joiningDate = new Date();
        this.ifActive = true;
    
        this.UserPassword= userPassword;

        this.username = userName;
        
        this.lastLoggin=new Date();


    }

    public User() {
       
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }

    public Date getJoiningDate(){
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate){
        this.joiningDate = joiningDate;
    }




    public boolean isIfActive() {
        return ifActive;
    }

    public void setIfActive(boolean ifActive){
        this.ifActive = ifActive;
    }

    public String getUserPassword(){
        return this.UserPassword;
    }

    public void setUserPassword(String userPassword) {
        this.UserPassword = userPassword;
    }

    public void setLastLoggin(){
        lastLoggin= new Date();
    }

   

  

    


    public Date getLastLoggin(){
        return this.lastLoggin;
    }

  


    public int getViewCount() {
        return viewCount;
    }


    
    public void addCount(){
        this.viewCount++;
    }
    public int getRate(){
        if(this.acceptanceCount ==0 || this.viewCount ==0 )
            return 1;
        return this.acceptanceCount/this.viewCount;
    }



   
    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
   

    public User getUser(User user){
        User temp = new User();
        return temp;
    }

    
}
