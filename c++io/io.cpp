//Simple writing in c++
#include <cstdlib>
#include <iostream>
#include <fstream>

int main ()
{
	std::cout<<"Hello World\n";
	std::ofstream target;
	target.open("target.txt");
	target<< "I'm updating client information!\n";
	target.close();
	return 0;
}
