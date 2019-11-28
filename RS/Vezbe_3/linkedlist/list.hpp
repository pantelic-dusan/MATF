#ifndef MATF_LIST_HPP
#define MATF_LIST_HPP

#include <memory>
#include <iostream>

namespace matf {

    class list
    {
    private:
        struct element {
            explicit element(int value=0, std::unique_ptr<element>&& next=nullptr);
            int value;
            std::unique_ptr<element> next;
        };

        element* at(const size_t index) const;
        std::unique_ptr<element> m_start = nullptr;
        size_t m_size = 0;
    public:
        list() = default;

        ~list();
        list(const list& other);
        list(list&& other) noexcept;
        list& operator=(const list& other);
        list& operator=(list&& other) noexcept;

        void push_front(const int value);
        void push_back(const int value);
        void pop_front();
        size_t size() const;

        int operator[] (const size_t index) const;
        int &operator[] (const  size_t index);
    };


}
std::ostream& operator<<(std::ostream& out, const matf::list& l);

#endif