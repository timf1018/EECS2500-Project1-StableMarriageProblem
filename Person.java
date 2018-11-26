public class Person {


    private String name;
    private int[]  preferences;
    private Person partner;
    private int position;


    public Person(String name, int position, int[] preferences) {
        this.name = name;
        this.preferences = preferences;
        this.partner = null;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPriority(int personVal) {
           return this.preferences[personVal];
   /*         for (int i = 0; i < this.preferences.length; i++) {
                if (this.preferences[i] == personVal) {
                    return i;
                }
            }
            return -1;*/
    }

    // returns the partner number that matches the priority value passed in
    public getPartnerIndex(int priorityVal) {
      for (int i = 0; i < this.preferences.length; i++) {
          if (this.preferences[i] == priorityVal) {
              return i;
          }
      }
      return -1;
    }

    public int[] getPreferences() {
        return preferences;
    }

    public Person getPartner() {
        return partner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreferences(int[] preferences) {
        this.preferences = preferences;
    }

    public void setPartner(Person partner) {
        this.partner = partner;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
