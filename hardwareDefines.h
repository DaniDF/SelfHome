#define DEBUG 1

#if !DEBUG
#include <bcm2835.h>
#endif

#ifndef MAX_TO_SHUT
	#define MAX_TO_SHUT 10
#endif

#ifndef MIN_PULSE_DURATION
	#define MIN_PULSE_DURATION 100000
#endif