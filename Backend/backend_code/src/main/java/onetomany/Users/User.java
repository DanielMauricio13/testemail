
package onetomany.Users;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import onetomany.Items.Item;
import onetomany.Reports.Reports;
import onetomany.userLogIn.userLogin;




@Entity
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    
    private Date joiningDate;
    private boolean ifActive;
    private String UserPassword;
    private Date lastLoggin;

    @Column(unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    userLogin  userLogin;

     @OneToMany(mappedBy = "user")
    List<Reports> userReports;
    
    @Column(unique = true)
    private String emailId;

     @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_items",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private Set<Item> likedItems = new HashSet<>();

    private String passwordRecoveryCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordRecoveryExpiry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserImage> images = new ArrayList<>();
   




 




    // =============================== Constructors ================================== //


    public User(String name, String emailId, String userPassword,String username ) {
        this.name = name;
        this.emailId = emailId;
        this.joiningDate = new Date();
        this.ifActive = true;
    
        userReports = new ArrayList<>();
        this.UserPassword= userPassword;

        this.username = username;
        
        this.lastLoggin=new Date();


    }

    public User() {
       
    }
     
    public void addItem(Item item){
        this.likedItems.add(item);
    }
    @JsonIgnore
    public Set<Item> getLikedItems(){
        return this.likedItems;
    }
    public void setLikedItems(Set<Item> items){
        this.likedItems=items;
    
    }
    
    public int getLikedItemsCount(){
        return this.likedItems.size();
    }
    public void removeItem(Item item){
        this.likedItems.remove(item);
    }
    public void removeAllItems(){
        this.likedItems.clear();
    }
    public void setUserLogin(userLogin userLogin) {
        this.userLogin = userLogin;
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

   
    public String getPasswordRecoveryCode() {
        return passwordRecoveryCode;
    }

    public void setPasswordRecoveryCode(String passwordRecoveryCode) {
        this.passwordRecoveryCode = passwordRecoveryCode;
    }

    public Date getPasswordRecoveryExpiry() {
        return passwordRecoveryExpiry;
    }

    public void setPasswordRecoveryExpiry(Date passwordRecoveryExpiry) {
        this.passwordRecoveryExpiry = passwordRecoveryExpiry;
    }

    public List<UserImage> getImages() {
        return images;
    }

    public void setImages(List<UserImage> images) {
        this.images = images;
    }

    public void addImage(UserImage image) {
        if (image != null) {
            images.add(image);
            image.setUser(this);
        }
    }

    public void removeImage(UserImage image) {
        if (image != null && images.remove(image)) {
            image.setUser(null);
        }
    }
   

    public User getUser(User user){
        User temp = new User();
        return temp;
    }
    public void removeReport(Reports reports){
        this.userReports.remove(reports);
    }

    
}
