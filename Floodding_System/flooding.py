import socket
import time
import sys

from multiprocessing import Process

flooding_value = 1
not_flooding_value = 0


def play_sound(ip, port):
    sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)

    value = 0
    while True:
        sock.sendto(str.encode("SET DISP sirena {};".format(value)), (ip, port))

        if value == 128:
            value = 0
        else:
            value = 128

        time.sleep(0.5)


def main(argv, buffer_size=1024):
    selfhome_ip = argv[1]
    selfhome_port = int(argv[2])

    sound_process = None
    old_response = not_flooding_value
    flag_stop = False

    while not flag_stop:
        sock = socket.socket(family=socket.AF_INET, type=socket.SOCK_DGRAM)
        sock.sendto(str.encode("GET DISP alluvione;"), (selfhome_ip, selfhome_port))
        msg, _ = sock.recvfrom(buffer_size)
        response = msg.decode()
        response = int(response[:len(response)-1])

        if response == flooding_value and old_response == flooding_value and sound_process is None:
            print("Activating alarm FLOODING")
            sound_process = Process(target=play_sound, args=(selfhome_ip, selfhome_port))
            sound_process.start()
        elif response == flooding_value:
            print("Received {}".format(response))

        if response == not_flooding_value and old_response == flooding_value:
            print("Deactivating alarm NO MORE FLOODING")
            sound_process.kill()
            sound_process = None
            sock.sendto(str.encode("SET DISP sirena 0;"), (selfhome_ip, selfhome_port))

        time.sleep(10)

        old_response = response


if __name__ == "__main__":
    main(sys.argv)
