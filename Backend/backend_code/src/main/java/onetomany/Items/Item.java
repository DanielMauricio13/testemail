
package onetomany.Items;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import onetomany.Sellers.Seller;
import onetomany.Users.User;




@Entity
@Table(name="Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private double price;
    private Date creationDate;
    private boolean ifAvailable;
    private String category;
    private int viewCount= 0;


    @Column(unique = true)
    private String username;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Seller seller;

   @ManyToMany
   @JoinTable(
          name = "user_liked_items",
         joinColumns = @JoinColumn(name = "item_id"),
         inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers;


    @Lob
    private byte[] profileImage;
    @ElementCollection
    private List<Integer> UserHobbiesLists;




 




    // =============================== Constructors ================================== //

public Item(String name, String description, double price, String category, String userName  ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = new Date();
        this.ifAvailable = true;
    
        this.category= category;

        this.username = userName;
        
       
    }
    public void addLikedByUser(User user){
        this.likedByUsers.add(user);
    }
    public Set<User> getLikedByUsers(){
        return this.likedByUsers;
    }
      public int getLikedByUsersCount(){
        return this.likedByUsers.size();
    }

    public Item() {

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

    

    public String getDescription(){
        return description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }


    public boolean isIfAvailable() {
        return ifAvailable;
    }

    public void setIfAvailable(boolean ifAvailable){
        this.ifAvailable = ifAvailable;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    
    public int getViewCount() {
        return viewCount;
    }


    
    public void addCount(){
        this.viewCount++;
    }
    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }


    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
   

    public byte[] getProfileImage() {
        return profileImage;
    }

    
}
