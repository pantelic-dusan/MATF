#include <vector>
#include <iostream>
#include <string>

class Vehicle {

    public:
        virtual ~Vehicle() = default;
        virtual std::string Type() const = 0;
        virtual Vehicle* Copy() const = 0;
        unsigned NumOfWheels() const {
            return m_num_of_wheels;
        }
        unsigned NumOfSeats() const {
            return m_num_of_seats;
        }


    protected:
        Vehicle(const unsigned num_of_wheels, const unsigned num_of_seats)
            :m_num_of_wheels(num_of_wheels), m_num_of_seats(num_of_seats) {

        }
        unsigned m_num_of_wheels;
        unsigned m_num_of_seats;

};

std::ostream& operator<<(std::ostream& out, const Vehicle& v) {
    return out << v.Type() << " " << v.NumOfSeats() << " " << v.NumOfWheels();
}

class Bicycle: public Vehicle {
    public:
        Bicycle(unsigned num_of_wheels=2u, unsigned num_of_seats=1u)
            :Vehicle(num_of_wheels, num_of_seats) {

        }

        std::string Type() const override {
            return "Bicycle";
        }

        Vehicle* Copy() const override {
            return new Bicycle(*this);
        }
};

std::ostream& operator<<(std::ostream& out, const Bicycle& b) {
    return out << b.Type() << " " << b.NumOfSeats() << " " << b.NumOfWheels();
}

class VehicleWithWindows: public Vehicle {
    public:
        unsigned NumOfWindows() const {
            return m_num_of_windows;
        }
    
    protected:
        unsigned m_num_of_windows;
        VehicleWithWindows(unsigned num_of_windows, unsigned num_of_wheels, unsigned num_of_seats)
            :Vehicle(num_of_wheels, num_of_seats), m_num_of_windows(num_of_windows) {

        }
};

std::ostream & operator<<(std::ostream & out, const VehicleWithWindows & vsp)
{
    return out << vsp.Type() << " " << vsp.NumOfSeats() << " " << vsp.NumOfWheels() << " " << vsp.NumOfWindows();
}

class Car: public VehicleWithWindows {
    public:
        Car(unsigned num_of_windows=6u, unsigned num_of_wheels=4u, unsigned num_of_seats=5u)
            :VehicleWithWindows(num_of_windows, num_of_wheels,num_of_seats) {

        }

        std::string Type() const override {
            return "Car";
        }

        Vehicle* Copy() const override {
            return new Car(*this);
        }
};

std::ostream & operator<<(std::ostream & out, const Car & a)
{
    return out << a.Type() << " " << a.NumOfSeats() << " " << a.NumOfWheels() << " " << a.NumOfWindows();
}

class Truck: public VehicleWithWindows {
    public:
        Truck(unsigned num_of_windows=3u, unsigned num_of_wheels=6u, unsigned num_of_seats=3u)
            :VehicleWithWindows(num_of_windows, num_of_wheels, num_of_seats) {

        }

        std::string Type() const override {
            return "Truck";
        }

        Vehicle* Copy() const override {
            return new Truck(*this);
        }
};

std::ostream & operator<<(std::ostream & out, const Truck & k)
{
    return out << k.Type() << " " << k.NumOfSeats() << " " << k.NumOfWheels() << " " << k.NumOfWindows();
}

class VehiclePark {
    public:
        VehiclePark() = default;

        VehiclePark(const VehiclePark& other) {
            for (const auto v: other.m_vehicles) {
                m_vehicles.push_back(v->Copy());
            }
        }

        ~VehiclePark() {
            for (auto v: m_vehicles) {
                delete v;
            }
            m_vehicles.clear();
        }

        VehiclePark(VehiclePark&& other) noexcept 
            :m_vehicles(std::move(other.m_vehicles)){
    
        }

        VehiclePark& operator=(VehiclePark& other) {
            auto tmp(other);
            std::swap(tmp.m_vehicles, m_vehicles);
            return *this;
        }

        VehiclePark& operator=(VehiclePark&& other) noexcept {
            m_vehicles = std::move(m_vehicles);
            return *this;
        }

        void Add(Vehicle *new_vehicle) {
            m_vehicles.push_back(new_vehicle);
        }
        
    private:
        std::vector<Vehicle*> m_vehicles;
        friend std::ostream & operator<<(std::ostream & out, const VehiclePark & vp);
};

std::ostream & operator<<(std::ostream & out, const VehiclePark & vp)
{
    for (const auto v : vp.m_vehicles)
    {
        out << *v << ", ";
    }
    return out;
}

int main()
{
    const Car a;
    const Truck k;
    const Bicycle b;

    const VehicleWithWindows* v = new Truck();

    std::cout << a << std::endl;
    std::cout << k << std::endl;
    std::cout << b << std::endl;
    std::cout << *v << std::endl;

    VehiclePark vp;

    vp.Add(new Car(a));
    vp.Add(new Truck(k));
    vp.Add(new Car());
    vp.Add(new Bicycle(4, 2));

    std::cout << vp << std::endl;

    auto vp2 = vp;

    vp2.Add(new Truck());

    std::cout << vp2 << std::endl;
    std::cout << vp << std::endl;

    delete v;

    return 0;
}
