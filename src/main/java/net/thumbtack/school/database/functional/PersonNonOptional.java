package net.thumbtack.school.database.functional;

public class PersonNonOptional {
    private PersonNonOptional father;
    private PersonNonOptional mother;

    public PersonNonOptional(PersonNonOptional father, PersonNonOptional mother) {
        this.father = father;
        this.mother = mother;
    }

    public PersonNonOptional getMothersMotherFather() {
        if (mother != null && mother.getMother() != null) {
                    return mother.getMother().getFather();
        } else {
                    return null;
        }
    }

    public PersonNonOptional getFather() {
        return father;
    }

    public PersonNonOptional getMother() {
        return mother;
    }
}
