//
// Created by 徐志强 on 2018/6/11.
//
typedef struct person{
    int age;
    char* name;
    int sex;
}Person;

void getAge(Person* person1){

}
char* min()
{
    Person p;
    p.name = "xuzhiqiang";
    p.age = 12;
    getAge(&p);
    return p.name;
}
