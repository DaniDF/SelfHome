//Automations
#define MAX_AUTOMATIONS 100
#define AUTO_FIELDS_SEPARATOR ';'

//Devices
#define MAX_DEVICE 20

#define MAX_NAME_LENGTH 32
#define MAX_GROUP_NAME_LENGTH 32
#define MAX_GROUP_PER_DEVICE 8
#define MAX_GROUP_SIZE 100

#define FIELDS_SEPARATOR ';'
#define GROUPS_SEPARATOR ':'

//                                  32         SEP            32                      8                  ALL_SEP = 7
#define MAX_FILE_LINE_LENGTH (MAX_NAME_LENGTH + 1 + MAX_GROUP_NAME_LENGTH * MAX_GROUP_PER_DEVICE + (MAX_GROUP_PER_DEVICE - 1) + 1 + 1 + 1 + 1 + 1) //L'ultimo +1 serve a conteggiare il terminatore di stringa
