package net.thumbtack.school.database.functional;

import java.util.Optional;

public class PersonWithOptional {
    private Optional<PersonWithOptional> father;
    private Optional<PersonWithOptional> mother;

    public PersonWithOptional(PersonWithOptional father, PersonWithOptional mother) {
        this.father = Optional.ofNullable(father);
        this.mother = Optional.ofNullable(mother);
    }

    public Optional<PersonWithOptional> getMothersMotherFather() {
        return mother.flatMap(PersonWithOptional::getMother).flatMap(PersonWithOptional::getFather);
    }

    public Optional<PersonWithOptional> getFather() {
        return father;
    }

    public Optional<PersonWithOptional> getMother() {
        return mother;
    }
}
